package com.ivo.coq.project.service.impl;

import com.ivo.common.enums.PlantEnum;
import com.ivo.coq.project.entity.*;
import com.ivo.coq.project.repository.EngineeringExperimentMaterialRepository;
import com.ivo.coq.project.repository.EngineeringExperimentProductRepository;
import com.ivo.coq.project.repository.EngineeringExperimentRepository;
import com.ivo.coq.project.repository.EngineeringExperimentWoRepository;
import com.ivo.coq.project.service.EngineeringExperimentService;
import com.ivo.coq.project.service.SampleService;
import com.ivo.rest.eifdb.EifService;
import com.ivo.rest.eifdb.entity.EifEngineeringExperimentMaterial;
import com.ivo.rest.eifdb.entity.EifEngineeringExperimentProduct;
import com.ivo.rest.oracle.OracleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class EngineeringExperimentServiceImpl implements EngineeringExperimentService {

    private EngineeringExperimentRepository repository;

    private EngineeringExperimentProductRepository productRepository;

    private EngineeringExperimentMaterialRepository materialRepository;

    private EngineeringExperimentWoRepository woRepository;

    private EifService eifService;

    private SampleService sampleService;

    private OracleService oracleService;

    @Autowired
    public EngineeringExperimentServiceImpl(EngineeringExperimentRepository repository,
                                            EngineeringExperimentProductRepository productRepository,
                                            EngineeringExperimentMaterialRepository materialRepository,
                                            EifService eifService, SampleService sampleService,
                                            EngineeringExperimentWoRepository woRepository,OracleService oracleService) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.materialRepository = materialRepository;
        this.eifService = eifService;
        this.sampleService = sampleService;
        this.woRepository = woRepository;
        this.oracleService = oracleService;
    }



    @Override
    public List<EngineeringExperiment> getEngineeringExperiments(String project) {
        return repository.findByProjectOrderBySample_Id(project);
    }

    @Override
    public List<EngineeringExperimentProduct> getEngineeringExperimentProduct(String project) {
        return productRepository.findByProject(project);
    }

    @Override
    public List<EngineeringExperimentMaterial> getEngineeringExperimentMaterial(String project) {
        return materialRepository.findByProject(project);
    }

    @Override
    public List<EngineeringExperimentWo> getEngineeringExperimentWo(String project) {
        return woRepository.findByProject(project);
    }

    @Override
    public void generateEngineeringExperiment(String project) {
        log.info("生成工程实验信息 " + project);
        repository.deleteAll(getEngineeringExperiments(project));
        List<Sample> sampleList = sampleService.getSamples(project);
        List<EngineeringExperiment> engineeringExperimentList = new ArrayList<>();
        for(Sample sample : sampleList) {
            // EE单
            if(StringUtils.startsWithIgnoreCase(sample.getOrderNumber(), "EE")) {
                EngineeringExperiment engineeringExperiment = new EngineeringExperiment(sample, sample.getOrderNumber(), project);
                engineeringExperimentList.add(engineeringExperiment);
            }
            // ED单
            else if(StringUtils.startsWithIgnoreCase(sample.getOrderNumber(), "ED")) {
                List<String> eeList = eifService.getEeByEd(sample.getOrderNumber());
                if(eeList== null || eeList.size() == 0) continue;
                for(String ee : eeList) {
                    EngineeringExperiment engineeringExperiment = new EngineeringExperiment(sample, ee, project);
                    engineeringExperimentList.add(engineeringExperiment);
                }
            }
        }
        repository.saveAll(engineeringExperimentList);
    }

    @Override
    public void syncEngineeringExperimentPlant(String project) {
        log.info("同步获取工程实验单的厂别 " + project);
        List<EngineeringExperiment> engineeringExperimentList = getEngineeringExperiments(project);
        for(EngineeringExperiment engineeringExperiment : engineeringExperimentList) {
            Map map = eifService.getPlantForEE(engineeringExperiment.getEeOrder());
            String plant = (String) map.get("plant");
            if (plant == null) plant = "";
            String storageLocation = (String) map.get("storageLocation_fk");
            if(storageLocation == null) storageLocation = "";
            engineeringExperiment.setPlant(plant.trim().toUpperCase());
            engineeringExperiment.setStorageLocation(storageLocation);
        }
        repository.saveAll(engineeringExperimentList);
    }

    @Override
    public void syncEngineeringExperimentProduct(String project) {
        log.info("同步获取工程实验单（ARRAY/CELL）的产品 " + project);
        productRepository.deleteAll(getEngineeringExperimentProduct(project));
        List<EngineeringExperiment> engineeringExperimentList = getEngineeringExperiments(project);
        List<EngineeringExperimentProduct> productList = new ArrayList<>();
        for(EngineeringExperiment engineeringExperiment : engineeringExperimentList) {
            if(!StringUtils.equalsAnyIgnoreCase(engineeringExperiment.getPlant(),
                    PlantEnum.Array.getPlant(), PlantEnum.Cell.getPlant())) continue;
            List<EifEngineeringExperimentProduct> eifList =
                    eifService.getEifEngineeringExperimentProduct(engineeringExperiment.getEeOrder());
            if(eifList == null || eifList.size() == 0) continue;
            for(EifEngineeringExperimentProduct eif : eifList) {
                EngineeringExperimentProduct product = new EngineeringExperimentProduct(engineeringExperiment, project,
                        engineeringExperiment.getEeOrder());
                product.setProduct(eif.getProduct().trim().toUpperCase());
                product.setCf(eif.getCf().trim().toUpperCase());
                product.setTft(eif.getTft().trim().toUpperCase());
                product.setExpQty(eif.getExpQty());
                productList.add(product);
            }
        }
        productRepository.saveAll(productList);
    }

    @Override
    public void syncEngineeringExperimentMaterial(String project) {
        log.info("同步获取工程实验单（CELL）的料号 " + project);
        materialRepository.deleteAll(getEngineeringExperimentMaterial(project));
        List<EngineeringExperiment> engineeringExperimentList = getEngineeringExperiments(project);
        List<EngineeringExperimentMaterial> materialList = new ArrayList<>();
        for(EngineeringExperiment engineeringExperiment : engineeringExperimentList) {
            if(!StringUtils.equalsIgnoreCase(engineeringExperiment.getPlant(), PlantEnum.Cell.getPlant())) continue;
            List<EifEngineeringExperimentMaterial> eifList =
                    eifService.getEifEngineeringExperimentMaterial(engineeringExperiment.getEeOrder());
            if(eifList == null || eifList.size() == 0) continue;
            for(EifEngineeringExperimentMaterial eif : eifList) {
                EngineeringExperimentMaterial material = new EngineeringExperimentMaterial(engineeringExperiment, project,
                        engineeringExperiment.getEeOrder());
                material.setExpQty(eif.getExpQty());
                material.setMaterialGroup(eif.getMaterialGroup().trim().toUpperCase());
                material.setMaterial(eif.getMaterial().trim().toUpperCase());
                materialList.add(material);
            }
        }
        materialRepository.saveAll(materialList);
    }

    @Override
    public void syncEngineeringExperimentWo(String project) {
        log.info("同步获取工程实验单（LCM）中的工单 " + project);
        woRepository.deleteAll(getEngineeringExperimentWo(project));
        List<EngineeringExperiment> engineeringExperimentList = getEngineeringExperiments(project);
        List<EngineeringExperimentWo> woList = new ArrayList<>();
        for(EngineeringExperiment engineeringExperiment : engineeringExperimentList) {
            if(!StringUtils.equalsIgnoreCase(engineeringExperiment.getPlant(), PlantEnum.Lcm.getPlant())) continue;
            List<String> wos = eifService.getEngineeringExperimentWo(engineeringExperiment.getEeOrder());
            if(wos == null || wos.size() == 0) continue;
            for(String woStr : wos) {
                woStr = woStr.trim().toUpperCase();
                EngineeringExperimentWo wo = new EngineeringExperimentWo(engineeringExperiment, project,
                        engineeringExperiment.getEeOrder());
                wo.setWo(woStr);
                String product = oracleService.getProductByWo(woStr);
                wo.setProduct(product);
                woList.add(wo);
            }
        }
        woRepository.saveAll(woList);
    }
}
