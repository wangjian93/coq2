package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.EngineeringExperimentProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface EngineeringExperimentProductRepository extends JpaRepository<EngineeringExperimentProduct, Long> {

    List<EngineeringExperimentProduct> findByProject(String project);

    @Query(value = "SELECT DISTINCT product from coq_project_Engineering_Experiment_Product where project=:project AND EE=:ee ", nativeQuery = true)
    List<String> getProduct(String project, String ee);
}
