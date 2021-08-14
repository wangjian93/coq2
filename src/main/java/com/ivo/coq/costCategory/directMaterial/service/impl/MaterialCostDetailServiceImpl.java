package com.ivo.coq.costCategory.directMaterial.service.impl;

import com.ivo.common.enums.PlantEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.directMaterial.entity.MaterialArrayProductAmount;
import com.ivo.coq.costCategory.directMaterial.entity.MaterialCellMaterialAmount;
import com.ivo.coq.costCategory.directMaterial.entity.MaterialLcmWoAmount;
import com.ivo.coq.costCategory.directMaterial.entity.MaterialCostDetail;
import com.ivo.coq.costCategory.directMaterial.repository.ArrayProductAmountRepository;
import com.ivo.coq.costCategory.directMaterial.repository.CellMaterialAmountRepository;
import com.ivo.coq.costCategory.directMaterial.repository.LcmWoAmountRepository;
import com.ivo.coq.costCategory.directMaterial.repository.MaterialCostDetailRepository;
import com.ivo.coq.costCategory.directMaterial.service.MaterialCostDetailService;
import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.entity.EngineeringExperimentMaterial;
import com.ivo.coq.project.entity.EngineeringExperimentProduct;
import com.ivo.coq.project.entity.EngineeringExperimentWo;
import com.ivo.coq.project.service.EngineeringExperimentService;
import com.ivo.rest.oracle.OracleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class MaterialCostDetailServiceImpl implements MaterialCostDetailService {

    private MaterialCostDetailRepository repository;

    private LcmWoAmountRepository lcmWoAmountRepository;

    private ArrayProductAmountRepository arrayProductAmountRepository;

    private CellMaterialAmountRepository cellMaterialAmountRepository;

    private EngineeringExperimentService engineeringExperimentService;

    private OracleService oracleService;

    @Autowired
    public MaterialCostDetailServiceImpl(MaterialCostDetailRepository repository, LcmWoAmountRepository lcmWoAmountRepository,
                                         ArrayProductAmountRepository arrayProductAmountRepository,
                                         CellMaterialAmountRepository cellMaterialAmountRepository,
                                         EngineeringExperimentService engineeringExperimentService,
                                         OracleService oracleService) {
        this.repository = repository;
        this.lcmWoAmountRepository = lcmWoAmountRepository;
        this.arrayProductAmountRepository = arrayProductAmountRepository;
        this.cellMaterialAmountRepository = cellMaterialAmountRepository;
        this.engineeringExperimentService = engineeringExperimentService;
        this.oracleService = oracleService;
    }

    @Override
    public List<MaterialCostDetail> getMaterialCostDetails(String project) {
        return repository.findByProject(project);
    }

    @Override
    public List<MaterialCostDetail> getMaterialCostDetails(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }

    @Override
    public void createMaterialCostDetail(String project) {
        log.info("创建厂内直材费用 " + project);
        repository.deleteAll(getMaterialCostDetails(project));
        List<EngineeringExperiment> engineeringExperimentList = engineeringExperimentService.getEngineeringExperiments(project);
        if(engineeringExperimentList== null || engineeringExperimentList.size() == 0) return;
        List<MaterialCostDetail> materialCostDetailList = new ArrayList<>();
        for(EngineeringExperiment engineeringExperiment : engineeringExperimentList) {
            //  模组入库2590仓不计算直材费用
            if(StringUtils.equals(engineeringExperiment.getPlant(), "LCM") &&
                    StringUtils.containsAny(engineeringExperiment.getStorageLocation(), "2600", "2590")) {
                System.out.println("直材料不算2590");
                continue;
            }

            MaterialCostDetail materialCostDetail = new MaterialCostDetail(engineeringExperiment);
            String plant = materialCostDetail.getPlant();
            if(StringUtils.equalsIgnoreCase(PlantEnum.Array.getPlant(), plant)) {
                materialCostDetail.setProductAmountList(createArrayProductAmount(materialCostDetail, engineeringExperiment));
            } else if(StringUtils.equalsIgnoreCase(PlantEnum.Cell.getPlant(), plant)) {
                materialCostDetail.setMaterialAmountList(createCellMaterialAmount(materialCostDetail, engineeringExperiment));
            } else if(StringUtils.equalsIgnoreCase(PlantEnum.Lcm.getPlant(), plant)) {
                materialCostDetail.setWoAmountList(createLcmWoAmount(materialCostDetail, engineeringExperiment));
            }
            materialCostDetailList.add(materialCostDetail);
        }
        repository.saveAll(materialCostDetailList);
    }

    private List<MaterialArrayProductAmount> createArrayProductAmount(MaterialCostDetail materialCostDetail, EngineeringExperiment engineeringExperiment) {
        List<EngineeringExperimentProduct> productList = engineeringExperiment.getProductList();
        if(productList == null || productList.size() == 0) {
            return null;
        }
        List<MaterialArrayProductAmount> materialArrayProductAmountList = new ArrayList<>();
        for(EngineeringExperimentProduct product : productList) {
            MaterialArrayProductAmount materialArrayProductAmount = new MaterialArrayProductAmount(product.getProject(), product.getEe(),
                    materialCostDetail);
            materialArrayProductAmount.setProduct(product.getProduct());
            materialArrayProductAmount.setQuantity(product.getExpQty());
            materialArrayProductAmountList.add(materialArrayProductAmount);
        }
        return materialArrayProductAmountList;
    }

    private List<MaterialLcmWoAmount> createLcmWoAmount(MaterialCostDetail materialCostDetail, EngineeringExperiment engineeringExperiment) {
        List<EngineeringExperimentWo> woList = engineeringExperiment.getWoList();
        if(woList == null || woList.size() == 0) {
            return null;
        }
        List<MaterialLcmWoAmount> materialLcmWoAmountList = new ArrayList<>();
        for(EngineeringExperimentWo wo : woList) {
            MaterialLcmWoAmount materialLcmWoAmount = new MaterialLcmWoAmount(wo.getProject(), wo.getEe(), materialCostDetail);
            materialLcmWoAmount.setWo(wo.getWo());
            materialLcmWoAmountList.add(materialLcmWoAmount);
        }
        return materialLcmWoAmountList;
    }

    private List<MaterialCellMaterialAmount> createCellMaterialAmount(MaterialCostDetail materialCostDetail, EngineeringExperiment engineeringExperiment) {
        List<EngineeringExperimentMaterial> materialList = engineeringExperiment.getMaterialList();
        if(materialList == null || materialList.size() == 0) {
            return null;
        }
        List<MaterialCellMaterialAmount> materialCellMaterialAmountList = new ArrayList<>();
        for(EngineeringExperimentMaterial material : materialList) {
            MaterialCellMaterialAmount materialCellMaterialAmount = new MaterialCellMaterialAmount(material.getProject(), material.getEe(), materialCostDetail);
            materialCellMaterialAmount.setMaterial(material.getMaterial());
            materialCellMaterialAmount.setQuantity(material.getExpQty());
            materialCellMaterialAmountList.add(materialCellMaterialAmount);
        }
        return materialCellMaterialAmountList;
    }

    @Override
    public void computeMaterialCostDetail(String project) {
        log.info("计算厂内直材费用 " + project);
        List<MaterialCostDetail> materialCostDetailList = getMaterialCostDetails(project);
        for(MaterialCostDetail materialCostDetail : materialCostDetailList) {
            String plant = materialCostDetail.getPlant();
            Double amount = null;
            if(StringUtils.equalsIgnoreCase(PlantEnum.Lcm.getPlant(), plant)) {
                List<MaterialLcmWoAmount> woAmountList = materialCostDetail.getWoAmountList();
                if (woAmountList == null) continue;
                for(MaterialLcmWoAmount woAmount : woAmountList) {
                    amount = DoubleUtil.sum(amount, woAmount.getWoAmount());
                }
            } else if(StringUtils.equalsIgnoreCase(PlantEnum.Array.getPlant(), plant)) {
                List<MaterialArrayProductAmount> productAmountList = materialCostDetail.getProductAmountList();
                if(productAmountList == null) continue;
                for(MaterialArrayProductAmount productAmount : productAmountList) {
                    amount = DoubleUtil.sum(amount, productAmount.getAmount());
                }
            } else if(StringUtils.equalsIgnoreCase(PlantEnum.Cell.getPlant(), plant)) {
                List<MaterialCellMaterialAmount> materialAmountList = materialCostDetail.getMaterialAmountList();
                if (materialAmountList == null) continue;
                for(MaterialCellMaterialAmount materialAmount : materialAmountList) {
                    amount = DoubleUtil.sum(amount, materialAmount.getAmount());
                }
            }
            materialCostDetail.setAmount(amount);
        }
        repository.saveAll(materialCostDetailList);
    }

    @Override
    public void syncArrayProductAmount(String project) {
        log.info("同步Array厂的材料单片费用 " + project);
        List<MaterialArrayProductAmount> materialArrayProductAmountList = arrayProductAmountRepository.findByProject(project);
        for(MaterialArrayProductAmount materialArrayProductAmount : materialArrayProductAmountList) {
            Double perAmount = oracleService.getProductPerAmount(materialArrayProductAmount.getProduct());
            materialArrayProductAmount.setPerAmount(perAmount);
            materialArrayProductAmount.setAmount(DoubleUtil.multiply(materialArrayProductAmount.getPerAmount(), materialArrayProductAmount.getQuantity()));
        }
        arrayProductAmountRepository.saveAll(materialArrayProductAmountList);
    }

    @Override
    public void syncLcmWoAmount(String project) {
        log.info("同步LCM厂的工单费用 " + project);
        List<MaterialLcmWoAmount> woAmountList = lcmWoAmountRepository.findByProject(project);
        for(MaterialLcmWoAmount materialLcmWoAmount : woAmountList) {
            Double woAmount = oracleService.getWoAmount(materialLcmWoAmount.getWo());
            materialLcmWoAmount.setWoAmount(woAmount);
        }
        lcmWoAmountRepository.saveAll(woAmountList);
    }

    @Override
    public void syncCellMaterialAmount(String project) {
        log.info("同步CELL厂的料号单价 " + project);
        List<MaterialCellMaterialAmount> materialCellMaterialAmountList = cellMaterialAmountRepository.findByProject(project);
        for(MaterialCellMaterialAmount materialCellMaterialAmount : materialCellMaterialAmountList) {
            Double materialPrice = oracleService.getMaterialPrice(materialCellMaterialAmount.getMaterial());
            materialCellMaterialAmount.setMaterialPrice(materialPrice);
            materialCellMaterialAmount.setAmount(DoubleUtil.multiply(materialCellMaterialAmount.getMaterialPrice(), materialCellMaterialAmount.getQuantity()));
        }
        cellMaterialAmountRepository.saveAll(materialCellMaterialAmountList);
    }
}
