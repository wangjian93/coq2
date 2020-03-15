package com.ivo.coq.costCategory.reworkScrap.service.impl;

import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCost;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostRepository;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostService;
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
public class ReworkScrapCostServiceImpl implements ReworkScrapCostService {

    private static Logger logger = LoggerFactory.getLogger(ReworkScrapCostServiceImpl.class);

    private ReworkScrapCostRepository repository;

    private ProjectService projectService;

    @Autowired
    public ReworkScrapCostServiceImpl(ReworkScrapCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<ReworkScrapCost> getReworkScraps(String project) {
        return repository.findByProject(project);
    }

    @Override
    public List<ReworkScrapCost> getReworkScrapCosts(String project, String stage) {
        return repository.findByProjectAndStage(project, stage);
    }

    @Override
    public ReworkScrapCost getReworkScrapCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createReworkScrapCost(String project) {
        logger.info("创建 ReworkScrapCost >> " + project);

        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<ReworkScrapCost> reworkScrapCostList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            ReworkScrapCost reworkScrapCost = new ReworkScrapCost(projectStage.getProject(), projectStage.getStage(), projectStage.getTime());

            String stage = reworkScrapCost.getStage();
            // 重工报废费用只有EVT/DVT/PVT/MP阶段
            if(!stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT") && !stage.equals("MP")) {
                reworkScrapCost.setReworkAmount(-1D);
                reworkScrapCost.setScrapAmount(-1D);
                reworkScrapCost.setAmount(-1D);
            }
            reworkScrapCostList.add(reworkScrapCost);
        }
        repository.saveAll(reworkScrapCostList);
    }
}
