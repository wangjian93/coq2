package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
