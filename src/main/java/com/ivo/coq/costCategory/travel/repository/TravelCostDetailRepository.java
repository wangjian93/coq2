package com.ivo.coq.costCategory.travel.repository;

import com.ivo.coq.costCategory.travel.entity.TravelCostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface TravelCostDetailRepository extends JpaRepository<TravelCostDetail, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List
     */
    List<TravelCostDetail> findByProjectOrderById(String project);
}
