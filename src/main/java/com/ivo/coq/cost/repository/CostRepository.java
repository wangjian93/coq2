package com.ivo.coq.cost.repository;

import com.ivo.coq.cost.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface CostRepository extends JpaRepository<Cost, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return Cost
     */
    Cost findFirstByProjectOrderById(String project);
}