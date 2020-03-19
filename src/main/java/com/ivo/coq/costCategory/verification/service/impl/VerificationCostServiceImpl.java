package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.verification.entity.VerificationCost;
import com.ivo.coq.costCategory.verification.repository.VerificationCostRepository;
import com.ivo.coq.costCategory.verification.service.VerificationCostService;
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
public class VerificationCostServiceImpl implements VerificationCostService {

    private static Logger logger = LoggerFactory.getLogger(VerificationCostServiceImpl.class);

    private VerificationCostRepository repository;

    private ProjectService projectService;

    @Autowired
    public VerificationCostServiceImpl(VerificationCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<VerificationCost> getVerificationCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<VerificationCost> getVerificationCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public VerificationCost getVerificationCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void crateVerificationCost(String project) {
        logger.info("创建 VerificationCost >> " + project);

        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<VerificationCost> verificationCostList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            VerificationCost verificationCost = new VerificationCost(projectStage.getProject(), projectStage.getStage(),
                    projectStage.getTime());

            String stage = verificationCost.getStage();
            // 验证费用只有EVT/DVT/PVT阶段
            if(!stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT")) {
                verificationCost.setBmVerificationAmount(-1D);
                verificationCost.setInPlantVerificationAmount(-1D);
                verificationCost.setAmount(-1D);
            }
            verificationCostList.add(verificationCost);
        }
        repository.saveAll(verificationCostList);
    }
}
