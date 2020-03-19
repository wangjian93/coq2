package com.ivo.coq.costCategory.reworkScrap.repository;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCostRepository extends JpaRepository<ReworkScrapCost, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<ReworkScrapCost>
     */
    List<ReworkScrapCost> findByProjectOrderById(String project);

    List<ReworkScrapCost> findByProjectAndStageOrderById(String project, String stage);

    /**
     * 根据机种、阶段、阶段次数筛选
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return ReworkScrapCost
     */
    ReworkScrapCost findFirstByProjectAndStageAndTime(String project, String stage, Integer time);
}
