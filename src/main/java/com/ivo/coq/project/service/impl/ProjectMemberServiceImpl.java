package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.ProjectMember;
import com.ivo.coq.project.repository.ProjectMemberRepository;
import com.ivo.coq.project.service.ProjectMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private ProjectMemberRepository repository;

    @Autowired
    public ProjectMemberServiceImpl(ProjectMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProjectMember> getMembers(String project) {
        return repository.findByProjectOrderById(project);
    }
}
