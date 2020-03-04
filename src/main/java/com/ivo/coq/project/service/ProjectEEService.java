package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.ProjectEE;

import java.util.List;

/**
 * 机种的EE信息服务接口
 * @author wj
 * @version 1.0
 */
public interface ProjectEEService {

    /**
     * 获取机种每个阶段的EE关联信息
     * @param project 机种名
     * @return List<ProjectEE>
     */
    List<ProjectEE> getProjectEEs(String project);
}
