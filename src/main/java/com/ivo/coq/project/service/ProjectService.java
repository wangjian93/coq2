package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.entity.ProjectStage;

import java.util.List;

/**
 * 机种服务接口
 * @author wj
 * @version 1.0
 */
public interface ProjectService {

    /**
     * 获取机种列表
     * @return List<Project>
     */
    List<Project> getProjects();

    /**
     * 获取机种的阶段
     * @param project 机种名
     * @return List<ProjectStage>
     */
    List<ProjectStage> getStages(String project);
}
