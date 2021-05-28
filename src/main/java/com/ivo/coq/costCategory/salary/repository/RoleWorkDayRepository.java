package com.ivo.coq.costCategory.salary.repository;

import com.ivo.coq.costCategory.salary.entity.RoleWorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface RoleWorkDayRepository extends JpaRepository<RoleWorkDay, Long> {

    RoleWorkDay findFirstByRole(String role);
}
