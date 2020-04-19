package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.Member;

import java.util.List;

/**
 * 机种项目成员服务接口
 * @author wj
 * @version 1.0
 */
public interface MemberService {

    /**
     * 获取机种的项目成员列表
     * @param project 机种名
     * @return List<Member>
     */
    List<Member> getMembers(String project);

    List<Member> getMembers(String project, String role);

    /**
     * 从PLM同步机种的项目成员
     * @param project 机种
     */
    void syncMember(String project);
}
