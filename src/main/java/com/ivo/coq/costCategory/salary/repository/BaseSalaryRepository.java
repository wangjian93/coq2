package com.ivo.coq.costCategory.salary.repository;

import com.ivo.coq.costCategory.salary.entity.BaseSalary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface BaseSalaryRepository extends JpaRepository<BaseSalary, Long> {

    BaseSalary findFirstByLabel(String label);

}
