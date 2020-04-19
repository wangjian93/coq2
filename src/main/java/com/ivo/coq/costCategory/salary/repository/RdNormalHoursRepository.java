package com.ivo.coq.costCategory.salary.repository;

import com.ivo.coq.costCategory.salary.entity.RdNormalHours;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface RdNormalHoursRepository extends JpaRepository<RdNormalHours, Long> {

    RdNormalHours findFirstByRole(String role);
}
