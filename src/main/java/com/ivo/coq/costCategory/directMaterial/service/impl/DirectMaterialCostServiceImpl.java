package com.ivo.coq.costCategory.directMaterial.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.directMaterial.entity.MaterialCostDetail;
import com.ivo.coq.costCategory.directMaterial.entity.OutsourcingThinningCostDetail;
import com.ivo.coq.costCategory.directMaterial.entity.ShipmentCostDetail;
import com.ivo.coq.costCategory.directMaterial.service.MaterialCostDetailService;
import com.ivo.coq.costCategory.directMaterial.service.OutsourcingThinningCostDetailService;
import com.ivo.coq.costCategory.directMaterial.service.ShipmentCostDetailService;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.directMaterial.entity.DirectMaterialCost;
import com.ivo.coq.costCategory.directMaterial.repository.DirectMaterialCostRepository;
import com.ivo.coq.costCategory.directMaterial.service.DirectMaterialCostService;
import lombok.extern.slf4j.Slf4j;
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
public class DirectMaterialCostServiceImpl implements DirectMaterialCostService {

    private DirectMaterialCostRepository repository;

    private ProjectService projectService;

    private MaterialCostDetailService materialCostDetailService;

    private OutsourcingThinningCostDetailService outsourcingThinningCostDetailService;

    private ShipmentCostDetailService shipmentCostDetailService;

    @Autowired
    public DirectMaterialCostServiceImpl(DirectMaterialCostRepository repository, ProjectService projectService,
                                         MaterialCostDetailService materialCostDetailService,
                                         OutsourcingThinningCostDetailService outsourcingThinningCostDetailService,
                                         ShipmentCostDetailService shipmentCostDetailService) {
        this.repository = repository;
        this.projectService = projectService;
        this.materialCostDetailService = materialCostDetailService;
        this.outsourcingThinningCostDetailService = outsourcingThinningCostDetailService;
        this.shipmentCostDetailService = shipmentCostDetailService;
    }

    @Override
    public List<DirectMaterialCost> getDirectMaterialCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<DirectMaterialCost> getDirectMaterialCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public DirectMaterialCost getDirectMaterialCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createDirectMaterialCost(String project) {
        log.info("创建 DirectMaterialCost >>" + project);
        repository.deleteAll(getDirectMaterialCosts(project));
        List<Stage> stageList = projectService.getProjectStages(project);
        List<DirectMaterialCost> directMaterialCostList = new ArrayList<>();
        for(Stage projectStage : stageList) {
            DirectMaterialCost directMaterialCost = new DirectMaterialCost(projectStage.getProject(), projectStage.getStage(),
                    projectStage.getTime());

            String stage = directMaterialCost.getStage();
            // 直接材料费用只有EVT/DVT/PVT阶段
            if(!stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT")) {
                directMaterialCost.setDirectAmount(-1D);
                directMaterialCost.setOutsourcingThinningAmount(-1D);
                directMaterialCost.setShipmentAmount(-1D);
                directMaterialCost.setAmount(-1D);
            }

            directMaterialCostList.add(directMaterialCost);
        }

        repository.saveAll(directMaterialCostList);
    }

    @Override
    public void computeDirectMaterialCost(String project) {
        log.info("计算 DirectMaterialCost >>" + project);
        List<DirectMaterialCost> directMaterialCostList = getDirectMaterialCosts(project);
        for(DirectMaterialCost directMaterialCost : directMaterialCostList) {
            // 厂内直材
            if(null == directMaterialCost.getDirectAmount() || -1 != directMaterialCost.getDirectAmount()) {
                directMaterialCost.setDirectAmount(null);
                List<MaterialCostDetail> materialCostDetailList = materialCostDetailService.getMaterialCostDetails(
                        directMaterialCost.getProject(), directMaterialCost.getStage(), directMaterialCost.getTime()
                );
                if(materialCostDetailList != null && materialCostDetailList.size() > 0) {
                    Double d = directMaterialCost.getDirectAmount();
                    for(MaterialCostDetail materialCostDetail : materialCostDetailList) {
                        d = DoubleUtil.sum(d , materialCostDetail.getAmount());
                    }
                    directMaterialCost.setDirectAmount(d);
                }
            }
            // 外包薄化
            if(null == directMaterialCost.getOutsourcingThinningAmount() || -1 != directMaterialCost.getOutsourcingThinningAmount()) {
                directMaterialCost.setOutsourcingThinningAmount(null);
                List<OutsourcingThinningCostDetail> outsourcingThinningCostDetailList =
                        outsourcingThinningCostDetailService.getOutsourcingThinningCostDetails(
                                directMaterialCost.getProject(), directMaterialCost.getStage(), directMaterialCost.getTime()
                        );
                if(outsourcingThinningCostDetailList != null && outsourcingThinningCostDetailList.size() > 0) {
                    Double d = directMaterialCost.getOutsourcingThinningAmount();
                    for(OutsourcingThinningCostDetail outsourcingThinningCostDetail : outsourcingThinningCostDetailList) {
                        d = DoubleUtil.sum(d , outsourcingThinningCostDetail.getPoAmount());
                    }
                    directMaterialCost.setOutsourcingThinningAmount(d);
                }
            }
            // 出货费用
            if(null == directMaterialCost.getShipmentAmount() || -1 != directMaterialCost.getShipmentAmount()) {
                directMaterialCost.setShipmentAmount(null);
                List<ShipmentCostDetail> shipmentCostDetailList = shipmentCostDetailService.getShipmentCostDetail(
                        directMaterialCost.getProject(), directMaterialCost.getStage(), directMaterialCost.getTime());
                if(shipmentCostDetailList != null && shipmentCostDetailList.size() > 0) {
                    Double d = directMaterialCost.getShipmentAmount();
                    for(ShipmentCostDetail shipmentCostDetail : shipmentCostDetailList) {
                        d = DoubleUtil.sum(d , shipmentCostDetail.getAmount());
                    }
                    directMaterialCost.setShipmentAmount(d);
                }
            }
            // 直接材料成本
            if(null == directMaterialCost.getAmount() || -1 != directMaterialCost.getAmount()) {
                Double amount = DoubleUtil.sum(directMaterialCost.getDirectAmount(), directMaterialCost.getOutsourcingThinningAmount());
                amount = DoubleUtil.subtract(amount, directMaterialCost.getShipmentAmount());
                directMaterialCost.setAmount(amount);
            }
        }
        repository.saveAll(directMaterialCostList);
    }
}
