package com.ivo.coq.costCategory.salary.repository;

import com.ivo.coq.costCategory.salary.entity.RoleSalary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface RoleSalaryRepository extends JpaRepository<RoleSalary, Long> {

    List<RoleSalary> findByValidFlagIsTrue();

    RoleSalary findByRoleAndValidFlagIsTrue(String role);
}
