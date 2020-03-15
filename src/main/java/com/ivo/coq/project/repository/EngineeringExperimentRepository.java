package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.EngineeringExperiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface EngineeringExperimentRepository extends JpaRepository<EngineeringExperiment, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<EngineeringExperiment>
     */
    List<EngineeringExperiment> findByProject(String project);
}
