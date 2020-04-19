package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.Member;
import com.ivo.coq.project.repository.MemberRepository;
import com.ivo.coq.project.service.MemberService;
import com.ivo.rest.plm.PlmService;
import com.ivo.rest.plm.entity.PlmMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private MemberRepository repository;

    private PlmService plmService;

    @Autowired
    public MemberServiceImpl(MemberRepository repository,
                             PlmService plmService) {
        this.repository = repository;
        this.plmService = plmService;
    }

    @Override
    public List<Member> getMembers(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<Member> getMembers(String project, String role) {
        return repository.findByProjectAndRole(project, role);
    }

    @Override
    public void syncMember(String project) {
        log.info("从PLM同步机种的项目成员 " + project);
        List<PlmMember> plmMemberList = plmService.getMember(project);
        repository.deleteAll(getMembers(project));
        List<Member> memberList = new ArrayList<>();
        for(PlmMember plmMember : plmMemberList) {
            Member member = new Member();
            member.setProject(project);
            member.setEmployeeId(plmMember.getUser().trim().toUpperCase());
            member.setEmployeeName(plmMember.getName().trim());
            member.setRole(plmMember.getRole().trim().toUpperCase());
            memberList.add(member);
        }
        repository.saveAll(memberList);
    }
}
