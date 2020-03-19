package com.ivo.coq.costCategory.reworkScrap.repository;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCostDetailRepository extends JpaRepository<ReworkScrapCostDetail, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<ReworkScrapCostDetail
     */
    List<ReworkScrapCostDetail> findByProjectOrderById(String project);
}
