package com.ivo.coq.costCategory.directMaterial.repository;

import com.ivo.coq.costCategory.directMaterial.entity.MaterialCellMaterialAmount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface CellMaterialAmountRepository extends JpaRepository<MaterialCellMaterialAmount, Long> {

    List<MaterialCellMaterialAmount> findByProject(String project);
}
