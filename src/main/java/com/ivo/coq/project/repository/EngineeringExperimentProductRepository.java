package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.EngineeringExperimentProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface EngineeringExperimentProductRepository extends JpaRepository<EngineeringExperimentProduct, Long> {

    List<EngineeringExperimentProduct> findByProject(String project);
}
