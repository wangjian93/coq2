package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.project.repository.ProjectRepository;
import com.ivo.coq.project.repository.StageRepository;
import com.ivo.coq.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    private StageRepository stageRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, StageRepository stageRepository) {
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getProjectsByType(String type) {
        return projectRepository.findByTypeOrderById(type);
    }

    @Override
    public List<Project> getProjectsByTypeAndSize(String type, String size) {
        return projectRepository.findByTypeAndSizeOrderById(type, size);
    }

    @Override
    public List<Stage> getProjectStages(String project) {
        List list = stageRepository.findByProject(project);
        Collections.sort(list);
        return list;
    }

    @Override
    public List<String> getStages(String project) {
        List<Stage> projectStageList = getProjectStages(project);
        List<String> stageList = new ArrayList<String>();
        for(Stage stage : projectStageList) {
            if(!stageList.contains(stage.getStage())) {
                stageList.add(stage.getStage());
            }
        }
        return stageList;
    }
}
