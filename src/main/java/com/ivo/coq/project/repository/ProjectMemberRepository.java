package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
}
