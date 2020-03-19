package com.ivo.coq.costCategory.salary.service.impl;

import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.salary.entity.SalaryCost;
import com.ivo.coq.costCategory.salary.repository.SalaryCostRepository;
import com.ivo.coq.costCategory.salary.service.SalaryCostService;
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
public class SalaryCostServiceImpl implements SalaryCostService {

    private static Logger logger = LoggerFactory.getLogger(SalaryCostServiceImpl.class);

    private SalaryCostRepository repository;

    private ProjectService projectService;

    @Autowired
    public SalaryCostServiceImpl(SalaryCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<SalaryCost> getSalaryCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public SalaryCost getSalaryCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createSalaryCost(String project) {
        logger.info("创建 SalaryCost >> " + project);

        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<SalaryCost> salaryCostList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            SalaryCost salaryCost = new SalaryCost(projectStage.getProject(), projectStage.getStage(), projectStage.getTime());

            String stage = salaryCost.getStage();
            // 人员工资费用只有NPRB/DESIGN阶段
            if(!stage.equals("NPRB") && !stage.equals("DESIGN")) {
                salaryCost.setInLossAmount(-1D);
                salaryCost.setPreventionAmount(-1D);
                salaryCost.setAmount(-1D);
            }
            salaryCostList.add(salaryCost);
        }
        repository.saveAll(salaryCostList);
    }
}
