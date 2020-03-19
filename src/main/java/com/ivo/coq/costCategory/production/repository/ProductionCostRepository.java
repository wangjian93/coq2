package com.ivo.coq.costCategory.production.repository;

import com.ivo.coq.costCategory.production.entity.ProductionCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ProductionCostRepository extends JpaRepository<ProductionCost, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<ProductionCost>
     */
    List<ProductionCost> findByProjectOrderById(String project);

    /**
     * 根据机种、阶段筛选
     * @param project 机种
     * @param stage 阶段
     * @return List<ProductionCost>
     */
    List<ProductionCost> findByProjectAndStageOrderById(String project, String stage);

    /**
     * 根据机种、阶段、阶段次数筛选
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return
     */
    ProductionCost findFirstByProjectAndStageAndTime(String project, String stage, Integer time);
}
