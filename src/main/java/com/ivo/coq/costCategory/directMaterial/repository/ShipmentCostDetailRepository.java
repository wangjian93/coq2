package com.ivo.coq.costCategory.directMaterial.repository;

import com.ivo.coq.costCategory.directMaterial.entity.ShipmentCostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ShipmentCostDetailRepository extends JpaRepository<ShipmentCostDetail, Long> {

    List<ShipmentCostDetail> findByProject(String project);
}
