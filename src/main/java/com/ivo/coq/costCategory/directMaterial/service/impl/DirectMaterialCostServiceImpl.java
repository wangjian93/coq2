package com.ivo.coq.costCategory.directMaterial.service.impl;

import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.compensation.service.impl.CompensationCostServiceImpl;
import com.ivo.coq.costCategory.directMaterial.entity.DirectMaterialCost;
import com.ivo.coq.costCategory.directMaterial.repository.DirectMaterialCostRepository;
import com.ivo.coq.costCategory.directMaterial.service.DirectMaterialCostService;
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
public class DirectMaterialCostServiceImpl implements DirectMaterialCostService {

    private static Logger logger = LoggerFactory.getLogger(CompensationCostServiceImpl.class);


    private DirectMaterialCostRepository repository;

    private ProjectService projectService;

    @Autowired
    public DirectMaterialCostServiceImpl(DirectMaterialCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<DirectMaterialCost> getDirectMaterialCosts(String project) {
        return repository.findByProject(project);
    }

    @Override
    public List<DirectMaterialCost> getDirectMaterialCosts(String project, String stage) {
        return repository.findByProjectAndStage(project, stage);
    }

    @Override
    public DirectMaterialCost getDirectMaterialCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createDirectMaterialCost(String project) {
        logger.info("创建 DirectMaterialCost >>" + project);

        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<DirectMaterialCost> directMaterialCostList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            DirectMaterialCost directMaterialCost = new DirectMaterialCost(projectStage.getProject(), projectStage.getStage(),
                    projectStage.getTime());

            String stage = directMaterialCost.getStage();
            // 直接材料费用只有EVT/DVT/PVT阶段
            if(!stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT")) {
                directMaterialCost.setDirectAmount(-1D);
                directMaterialCost.setOutsourcingThinningAmount(-1D);
                directMaterialCost.setAmount(-1D);
            }

            directMaterialCostList.add(directMaterialCost);
        }

        repository.saveAll(directMaterialCostList);
    }
}
