package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByProjectOrderById(String project);

    List<Member> findByProjectAndRole(String project, String role);
}
