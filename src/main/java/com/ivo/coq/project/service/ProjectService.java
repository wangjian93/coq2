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
     * 获取机种列表, 根据机种类型
     * @param type 类型
     * @return List<Project>
     */
    List<Project> getProjectsByType(String type);

    /**
     * 获取机种列表，根据机种类型和机种尺寸
     * @param type 类型
     * @param size 尺寸
     * @return List<Project>
     */
    List<Project> getProjectsByTypeAndSize(String type, String size);

    /**
     * 获取机种的阶段
     * @param project 机种名
     * @return List<ProjectStage>
     */
    List<ProjectStage> getProjectStages(String project);

    /**
     * 获取机种的阶段，不带次数
     * @param project 机种
     * @return 阶段
     */
    List<String> getStages(String project);
}
