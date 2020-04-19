package com.ivo.coq.costCategory.salary.repository;

import com.ivo.coq.costCategory.salary.entity.RoleWorkDays;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface RoleWorkDaysRepository extends JpaRepository<RoleWorkDays, Long> {

    RoleWorkDays findFirstByRole(String role);
}
