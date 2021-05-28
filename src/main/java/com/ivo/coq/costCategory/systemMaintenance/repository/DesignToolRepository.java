package com.ivo.coq.costCategory.systemMaintenance.repository;

import com.ivo.coq.costCategory.systemMaintenance.entity.DesignTool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface DesignToolRepository extends JpaRepository<DesignTool, Long> {

    List<DesignTool> findByValidFlagIsTrue();
}
