package com.ivo.coq.costCategory.production.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.production.entity.ProductionCostDetail;
import com.ivo.coq.costCategory.production.service.ProductionCostDetailService;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.production.entity.ProductionCost;
import com.ivo.coq.costCategory.production.repository.ProductionCostRepository;
import com.ivo.coq.costCategory.production.service.ProductionCostService;
import com.ivo.coq.project.service.StageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ProductionCostServiceImpl implements ProductionCostService {

    private static Logger logger = LoggerFactory.getLogger(ProductionCostServiceImpl.class);

    private ProductionCostRepository repository;

    private StageService stageService;

    private ProductionCostDetailService productionCostDetailService;

    @Autowired
    public ProductionCostServiceImpl(ProductionCostRepository repository, StageService stageService,
                                     ProductionCostDetailService productionCostDetailService) {
        this.repository = repository;
        this.stageService = stageService;
        this.productionCostDetailService = productionCostDetailService;
    }

    @Override
    public List<ProductionCost> getProductionCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public ProductionCost getProductionCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public List<ProductionCost> getProductionCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public void createProductionCost(String project) {
        logger.info("创建 ProductionCost >> " + project);
        repository.deleteAll(getProductionCosts(project));
        List<Stage> stageList = stageService.getStage(project);
        List<ProductionCost> productionCostList = new ArrayList<>();
        for(Stage stage : stageList) {
            ProductionCost productionCost = new ProductionCost(stage.getProject(), stage.getStage(), stage.getTime());
            // 生产费用只有EVT/DVT/PVT阶段
            if(!StringUtils.equalsAnyIgnoreCase(stage.getStage(), StageEnum.EVT.getStage(), StageEnum.DVT.getStage(),
                    StageEnum.PVT.getStage())) {
                productionCost.setAmount(-1D);
            }
            productionCostList.add(productionCost);
        }
        repository.saveAll(productionCostList);
    }

    @Override
    public void computeProductionCost(String project) {
        logger.info("计算机种的生产费用 >> " + project);
        List<ProductionCost> productionCostList = getProductionCosts(project);
        for(ProductionCost productionCost : productionCostList) {
            List<ProductionCostDetail> detailList = productionCostDetailService.getProductionCostDetail(productionCost.getProject(),
                    productionCost.getStage(), productionCost.getTime());
            if(productionCost.getAmount() != null && productionCost.getAmount() == -1) continue;
            Double amount = null;
            for(ProductionCostDetail detail : detailList) {
                amount = DoubleUtil.sum(amount, detail.getAmount());
            }
            productionCost.setAmount(amount);
        }
        repository.saveAll(productionCostList);
    }
}
