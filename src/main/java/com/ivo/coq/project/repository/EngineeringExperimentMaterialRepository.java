package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.EngineeringExperimentMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface EngineeringExperimentMaterialRepository extends JpaRepository<EngineeringExperimentMaterial, Long> {

    List<EngineeringExperimentMaterial> findByProject(String project);
}
