package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.ProjectMember;

import java.util.List;

/**
 * 机种项目成员服务接口
 * @author wj
 * @version 1.0
 */
public interface ProjectMemberService {

    /**
     * 获取机种的项目成员列表
     * @param project 机种名
     * @return List<ProjectMember>
     */
    List<ProjectMember> getMembers(String project);
}
