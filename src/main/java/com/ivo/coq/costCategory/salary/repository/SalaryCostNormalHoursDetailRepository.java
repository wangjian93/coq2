package com.ivo.coq.costCategory.salary.repository;

import com.ivo.coq.costCategory.salary.entity.SalaryCostNormalHoursDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface SalaryCostNormalHoursDetailRepository extends JpaRepository<SalaryCostNormalHoursDetail, Long> {

    List<SalaryCostNormalHoursDetail> findByProject(String project);
}
