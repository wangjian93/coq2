package com.ivo.coq.costCategory.systemMaintenance.serivce.impl;

import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.systemMaintenance.entity.SystemMaintenanceCost;
import com.ivo.coq.costCategory.systemMaintenance.repository.SystemMaintenanceCostRepository;
import com.ivo.coq.costCategory.systemMaintenance.serivce.SystemMaintenanceCostService;
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
public class SystemMaintenanceCostServiceImpl implements SystemMaintenanceCostService {

    private static Logger logger = LoggerFactory.getLogger(SystemMaintenanceCostServiceImpl.class);

    private SystemMaintenanceCostRepository repository;

    private ProjectService projectService;

    @Autowired
    public SystemMaintenanceCostServiceImpl(SystemMaintenanceCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<SystemMaintenanceCost> getSystemMaintenanceCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public SystemMaintenanceCost getSystemMaintenanceCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createSystemMaintenanceCos(String project) {
        logger.info("创建 SystemMaintenanceCost >> " + project);

        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<SystemMaintenanceCost> systemMaintenanceCostList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            SystemMaintenanceCost systemMaintenanceCost = new SystemMaintenanceCost(projectStage.getProject(),
                    projectStage.getStage(), projectStage.getTime());

            String stage = systemMaintenanceCost.getStage();
            // 人员工资费用只有DESIGN阶段
            if(!stage.equals("DESIGN")) {
                systemMaintenanceCost.setAmount(-1D);
                systemMaintenanceCost.setInLossAmount(-1D);
                systemMaintenanceCost.setIdentityAmount(-1D);
            }
            systemMaintenanceCostList.add(systemMaintenanceCost);
        }
        repository.saveAll(systemMaintenanceCostList);
    }
}
