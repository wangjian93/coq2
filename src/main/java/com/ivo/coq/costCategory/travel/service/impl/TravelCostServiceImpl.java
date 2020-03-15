package com.ivo.coq.costCategory.travel.service.impl;

import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.travel.entity.TravelCost;
import com.ivo.coq.costCategory.travel.repository.TravelCostRepository;
import com.ivo.coq.costCategory.travel.service.TravelCostService;
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
public class TravelCostServiceImpl implements TravelCostService {

    private static Logger logger = LoggerFactory.getLogger(TravelCostServiceImpl.class);

    private TravelCostRepository repository;

    private ProjectService projectService;

    @Autowired
    public TravelCostServiceImpl(TravelCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<TravelCost> getTravelCosts(String project) {
        return repository.findByProject(project);
    }

    @Override
    public TravelCost getTravelCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createTravelCost(String project) {
        logger.info("创建 TravelCost >> " + project);

        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<TravelCost> travelCostList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            TravelCost travelCost = new TravelCost(projectStage.getProject(), projectStage.getStage(), projectStage.getTime());

            String stage = travelCost.getStage();
            // 差旅交际费用只有NPRB阶段
            if(!stage.equals("NPRB")) {
                travelCost.setPreventionAmount(-1D);
                travelCost.setInLossAmount(-1D);
                travelCost.setAmount(-1D);
            }
            travelCostList.add(travelCost);
        }
        repository.saveAll(travelCostList);
    }
}
