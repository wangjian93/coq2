package com.ivo.coq.costCategory.production.service.impl;

import com.ivo.common.enums.PlantEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.production.entity.ProductionCostDetail;
import com.ivo.coq.costCategory.production.repository.ProductionCostDetailRepository;
import com.ivo.coq.costCategory.production.service.ProductionCostDetailService;
import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.entity.EngineeringExperimentWo;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.SampleService;
import com.ivo.rest.oracle.OracleService;
import com.ivo.station.service.StationCostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class ProductionCostDetailServiceImpl implements ProductionCostDetailService {

    private ProductionCostDetailRepository repository;

    private SampleService sampleService;

    @Resource
    private StationCostService stationCostService;

    @Resource
    private OracleService oracleService;

    @Autowired
    public ProductionCostDetailServiceImpl(ProductionCostDetailRepository repository, SampleService sampleService) {
        this.repository = repository;
        this.sampleService =  sampleService;
    }

    @Override
    public List<ProductionCostDetail> getProductionCostDetail(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<ProductionCostDetail> getProductionCostDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void syncProductionCostDetail(String project) {
        log.info("同步创建生产费用详细 " + project);
        repository.deleteAll(getProductionCostDetail(project));
        List<Sample> sampleList = sampleService.getSamples(project);
        if(sampleList == null) return;
        List<ProductionCostDetail> list = new ArrayList<>();
        for(Sample sample : sampleList) {
            List<EngineeringExperiment> engineeringExperimentList = sample.getEngineeringExperimentList();
            if(engineeringExperimentList == null || engineeringExperimentList.size()==0) continue;
            String fab = engineeringExperimentList.get(0).getPlant();
            //LCM按工单计算生产费用
            if(PlantEnum.Lcm.getPlant().equals(fab)) {
                for(EngineeringExperiment engineeringExperiment : engineeringExperimentList) {
                    //  模组入库2590仓不计算生产费用
                    if(StringUtils.equals(engineeringExperiment.getPlant(), "LCM") &&
                            StringUtils.containsAny(engineeringExperiment.getStorageLocation(), "2600","2590")) {
                        System.out.println("生产费用不算2600");
                        continue;
                    }

                    List<EngineeringExperimentWo> woList = engineeringExperiment.getWoList();
                    for(EngineeringExperimentWo wo : woList) {
                        ProductionCostDetail productionCostDetail = new ProductionCostDetail(sample.getProject(), sample.getStage(), sample.getTime());
                        productionCostDetail.setProcess(sample.getProcess());
                        productionCostDetail.setOrderNumber(sample.getOrderNumber());
                        productionCostDetail.setInQuantity(sample.getInQuantity());
                        productionCostDetail.setInDate(sample.getInDate());
                        productionCostDetail.setPlant(fab);
                        productionCostDetail.setWo(wo.getWo());
                        productionCostDetail.setProductId(wo.getProduct());
                        list.add(productionCostDetail);
                    }
                }
            } else {
                ProductionCostDetail productionCostDetail = new ProductionCostDetail(sample.getProject(), sample.getStage(), sample.getTime());
                productionCostDetail.setProcess(sample.getProcess());
                productionCostDetail.setOrderNumber(sample.getOrderNumber());
                productionCostDetail.setInQuantity(sample.getInQuantity());
                productionCostDetail.setInDate(sample.getInDate());
                productionCostDetail.setPlant(fab);
                list.add(productionCostDetail);
            }
        }
        repository.saveAll(list);
    }

    @Override
    public void computeProductionCostDetail(String project) {
        log.info("计算生产费用详细 " + project);
        List<ProductionCostDetail> productionCostDetailList = getProductionCostDetail(project);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        for(ProductionCostDetail productionCostDetail : productionCostDetailList) {
            String fab = productionCostDetail.getPlant();
            Double perProductAmount = 0D;
            Double amount = 0D;
            Double shippingQty = 0D;
            String month = sdf.format(productionCostDetail.getInQuantity());
            if(StringUtils.containsIgnoreCase(project, " ")) {
                project = project.split(" ")[0];
            }
            if(PlantEnum.Array.getPlant().equals(fab)) {
                perProductAmount  = stationCostService.getPerProductAmountAry(month, project);
                amount = DoubleUtil.multiply(perProductAmount, productionCostDetail.getInQuantity());
            }
            else if(PlantEnum.Cell.getPlant().equals(fab)) {
                perProductAmount  = stationCostService.getPerProductAmountCell(project, null, "");
                amount = DoubleUtil.multiply(perProductAmount, productionCostDetail.getInQuantity());
            }
            else if(PlantEnum.Lcm.getPlant().equals(fab)) {
                //LCM的计算按每张工单来  上产费用=工单Shipping数量*工单product单片生产费用
                String wo = productionCostDetail.getWo();
                String product = productionCostDetail.getProductId();
                shippingQty = oracleService.getWoShippingQty(wo);
                shippingQty = shippingQty == null ? 0d : shippingQty;
                perProductAmount  = stationCostService.getPerProductAmountLcm(month, product);
                amount = DoubleUtil.multiply(perProductAmount, shippingQty);
                productionCostDetail.setWoShippingQty(shippingQty);
            }

            productionCostDetail.setPerAmount(perProductAmount);
            productionCostDetail.setAmount(amount);
        }
        repository.saveAll(productionCostDetailList);
    }
}
