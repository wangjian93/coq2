package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.repository.ProjectRepository;
import com.ivo.coq.project.repository.ProjectStageRepository;
import com.ivo.coq.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    private ProjectStageRepository stageRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectStageRepository stageRepository) {
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getProjectsByType(String type) {
        return projectRepository.findByType(type);
    }

    @Override
    public List<Project> getProjectsByTypeAndSize(String type, String size) {
        return projectRepository.findByTypeAndSize(type, size);
    }

    @Override
    public List<ProjectStage> getProjectStages(String project) {
        return stageRepository.findByProject(project);
    }

    @Override
    public List<String> getStages(String project) {
        List<ProjectStage> projectStageList = getProjectStages(project);
        List<String> stageList = new ArrayList<String>();
        for(ProjectStage projectStage : projectStageList) {
            if(!stageList.contains(projectStage.getStage())) {
                stageList.add(projectStage.getStage());
            }
        }
        return stageList;
    }
}
