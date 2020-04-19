package com.ivo.coq.costCategory.directMaterial.repository;

import com.ivo.coq.costCategory.directMaterial.entity.MaterialArrayProductAmount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ArrayProductAmountRepository extends JpaRepository<MaterialArrayProductAmount, Long> {

    List<MaterialArrayProductAmount> findByProject(String project);
}
