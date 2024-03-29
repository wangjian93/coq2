package com.ivo.coq.costCategory.salary.repository;

import com.ivo.coq.costCategory.salary.entity.DesignWorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface DesignWorkDayRepository extends JpaRepository<DesignWorkDay, Long> {

    List<DesignWorkDay> findByValidFlagIsTrue();

    DesignWorkDay findByRoleAndValidFlagIsTrue(String role);
}
