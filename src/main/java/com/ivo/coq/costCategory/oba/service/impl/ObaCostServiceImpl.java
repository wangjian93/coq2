package com.ivo.coq.costCategory.oba.service.impl;

import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.oba.entity.ObaCost;
import com.ivo.coq.costCategory.oba.repository.ObaCostRepository;
import com.ivo.coq.costCategory.oba.service.ObaCostService;
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
public class ObaCostServiceImpl implements ObaCostService {

    private static Logger logger = LoggerFactory.getLogger(ObaCostServiceImpl.class);

    private ObaCostRepository repository;

    private ProjectService projectService;

    @Autowired
    public ObaCostServiceImpl(ObaCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<ObaCost> getObaCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<ObaCost> getObaCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public ObaCost getObaCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createObaCost(String project) {
        logger.info("创建 ObaCost >> " + project);

        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<ObaCost> obaCostList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            ObaCost obaCost = new ObaCost(projectStage.getProject(), projectStage.getStage(), projectStage.getTime());

            String stage = obaCost.getStage();
            // OBA费用只有EVT/DVT/PVT/MP阶段
            if(!stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT") && !stage.equals("MP")) {
                obaCost.setAmount(-1D);
            }
            obaCostList.add(obaCost);
        }

        repository.saveAll(obaCostList);
    }
}
