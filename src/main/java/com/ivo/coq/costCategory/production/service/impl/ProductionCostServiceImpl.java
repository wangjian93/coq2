package com.ivo.coq.costCategory.production.service.impl;

import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.production.entity.ProductionCost;
import com.ivo.coq.costCategory.production.repository.ProductionCostRepository;
import com.ivo.coq.costCategory.production.service.ProductionCostService;
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

    private ProjectService projectService;

    @Autowired
    public ProductionCostServiceImpl(ProductionCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
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

        List<Stage> stageList = projectService.getProjectStages(project);
        List<ProductionCost> productionCostList = new ArrayList<>();
        for(Stage projectStage : stageList) {
            ProductionCost productionCost = new ProductionCost(projectStage.getProject(), projectStage.getStage(), projectStage.getTime());

            String stage = productionCost.getStage();
            // 生产费用只有EVT/DVT/PVT阶段
            if(!stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT")) {
                productionCost.setAmount(-1D);
            }
            productionCostList.add(productionCost);
        }

        repository.saveAll(productionCostList);
    }
}
