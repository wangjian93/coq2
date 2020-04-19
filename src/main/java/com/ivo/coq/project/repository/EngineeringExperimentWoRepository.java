package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.EngineeringExperimentWo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface EngineeringExperimentWoRepository extends JpaRepository<EngineeringExperimentWo, Long> {

    List<EngineeringExperimentWo> findByProject(String project);
}
