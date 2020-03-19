package com.ivo.coq.costCategory.travel.repository;

import com.ivo.coq.costCategory.travel.entity.TravelCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface TravelCostRepository extends JpaRepository<TravelCost, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<TravelCost>
     */
    List<TravelCost> findByProjectOrderById(String project);

    /**
     * 根据机种、阶段、阶段次数筛选
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return TravelCost
     */
    TravelCost findFirstByProjectAndStageAndTime(String project, String stage, Integer time);
}
