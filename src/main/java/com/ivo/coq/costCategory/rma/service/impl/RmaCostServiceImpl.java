package com.ivo.coq.costCategory.rma.service.impl;

import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.rma.entity.RmaCost;
import com.ivo.coq.costCategory.rma.repository.RmaCostRepository;
import com.ivo.coq.costCategory.rma.service.RmaCostService;
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
public class RmaCostServiceImpl implements RmaCostService {

    private static Logger logger = LoggerFactory.getLogger(RmaCostServiceImpl.class);

    private RmaCostRepository repository;

    private ProjectService projectService;

    @Autowired
    public RmaCostServiceImpl(RmaCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<RmaCost> getRmaCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<RmaCost> getRmaCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public RmaCost getRmaCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createRmaCost(String project) {
        logger.info("创建 RmaCost >> " + project);

        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<RmaCost> rmaCostList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            RmaCost rmaCost = new RmaCost(projectStage.getProject(), projectStage.getStage(), projectStage.getTime());

            String stage = rmaCost.getStage();
            // 重工报废费用只有EVT/DVT/PVT/MP阶段
            if(!stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT") && !stage.equals("MP")) {
                rmaCost.setAmount(-1D);
            }
            rmaCostList.add(rmaCost);
        }
        repository.saveAll(rmaCostList);
    }
}
