package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface EngineeringExperimentRepository extends JpaRepository<EngineeringExperiment, Long> {

    List<EngineeringExperiment> findByProject(String project);
}
