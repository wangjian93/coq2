package com.ivo.coq.costCategory.compensation.service.impl;

import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.compensation.entity.CompensationCost;
import com.ivo.coq.costCategory.compensation.repository.CompensationCostRepository;
import com.ivo.coq.costCategory.compensation.service.CompensationCostSerivce;
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
public class CompensationCostServiceImpl implements CompensationCostSerivce {

    private static Logger logger = LoggerFactory.getLogger(CompensationCostServiceImpl.class);


    private CompensationCostRepository repository;

    private ProjectService projectService;

    @Autowired
    public CompensationCostServiceImpl(CompensationCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<CompensationCost> getCompensationCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<CompensationCost> getCompensationCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public CompensationCost getCompensationCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createCompensationCost(String project) {
        logger.info("创建 CompensationCost >>" + project);
        List<Stage> stageList = projectService.getProjectStages(project);
        List<CompensationCost> compensationCostList = new ArrayList<>();
        for(Stage projectStage : stageList) {
            CompensationCost compensationCost = new CompensationCost(projectStage.getProject(), projectStage.getStage(),
                    projectStage.getTime());

            String stage = compensationCost.getStage();
            // 赔偿费用只有EVT/DVT/PVT/MP阶段
            if(!stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT") && !stage.equals("MP")) {
                compensationCost.setAmount(-1D);
            }

            // 目前不计算赔偿费用
            compensationCost.setAmount(-1D);
            compensationCostList.add(compensationCost);
        }

        repository.saveAll(compensationCostList);
    }
}
