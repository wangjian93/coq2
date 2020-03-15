package com.ivo.coq.costCategory.jig.service.impl;

import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.jig.entity.JigCost;
import com.ivo.coq.costCategory.jig.repository.JigCostRepository;
import com.ivo.coq.costCategory.jig.service.JigCostService;
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
public class JigCostServiceImpl implements JigCostService {

    private static Logger logger = LoggerFactory.getLogger(JigCostServiceImpl.class);


    private JigCostRepository repository;

    private ProjectService projectService;


    @Autowired
    public JigCostServiceImpl(JigCostRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<JigCost> getJigCosts(String project) {
        return repository.findByProject(project);
    }

    @Override
    public JigCost getJigCost(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public List<JigCost> getJigCosts(String project, String stage) {
        return repository.findByProjectAndStage(project, stage);
    }

    @Override
    public void createJigCost(String project) {
        logger.info("创建 JigCost >> " + project);

        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<JigCost> jigCostList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            JigCost jigCost = new JigCost(projectStage.getProject(), projectStage.getStage(), projectStage.getTime());

            String stage = jigCost.getStage();
            // 治工具费用只有DESIGN/EVT/DVT/PVT阶段
            if(!stage.equals("DESIGN") && !stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT")) {
                jigCost.setAmount(-1D);
            }

            jigCostList.add(jigCost);
        }
        repository.saveAll(jigCostList);
    }
}
