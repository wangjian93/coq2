package com.ivo.coq.costCategory.salary.repository;

import com.ivo.coq.costCategory.salary.entity.SalaryCostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface SalaryCostDetailRepository extends JpaRepository<SalaryCostDetail, Long> {
    List<SalaryCostDetail> findByProject(String project);
}
