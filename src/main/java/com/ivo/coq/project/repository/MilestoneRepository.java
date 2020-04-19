package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    List<Milestone> findByProjectOrderById(String project);

    Milestone findByProjectAndMilestone(String project, String milestone);
}
