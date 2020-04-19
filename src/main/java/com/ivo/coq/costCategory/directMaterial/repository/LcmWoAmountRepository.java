package com.ivo.coq.costCategory.directMaterial.repository;

import com.ivo.coq.costCategory.directMaterial.entity.MaterialLcmWoAmount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface LcmWoAmountRepository extends JpaRepository<MaterialLcmWoAmount,  Long> {

    List<MaterialLcmWoAmount> findByProject(String project);
}
