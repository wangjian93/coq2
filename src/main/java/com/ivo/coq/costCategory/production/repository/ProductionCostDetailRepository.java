package com.ivo.coq.costCategory.production.repository;

import com.ivo.coq.costCategory.production.entity.ProductionCostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ProductionCostDetailRepository extends JpaRepository<ProductionCostDetail, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<ProductionCostDetailRepository>
     */
    List<ProductionCostDetail> findByProjectOrderById(String project);



    List<ProductionCostDetail> findByProjectAndStageAndTime(String project, String stage, Integer time);
}
