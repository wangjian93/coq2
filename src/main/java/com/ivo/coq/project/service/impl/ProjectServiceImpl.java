package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Override
    public List<Project> getProjects() {
        // TODO...
        return null;
    }

    @Override
    public List<ProjectStage> getStages(String project) {
        // TODO...
        return null;
    }
}
