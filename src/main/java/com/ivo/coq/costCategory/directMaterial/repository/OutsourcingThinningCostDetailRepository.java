package com.ivo.coq.costCategory.directMaterial.repository;

import com.ivo.coq.costCategory.directMaterial.entity.OutsourcingThinningCostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface OutsourcingThinningCostDetailRepository extends JpaRepository<OutsourcingThinningCostDetail, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List
     */
    List<OutsourcingThinningCostDetail> findByProjectOrderById(String project);

    /**
     * 根据机种、阶段、次数筛选
     * @param project 机种
     * @param stage 阶段
     * @param time 次数
     * @return List
     */
    List<OutsourcingThinningCostDetail> findByProjectAndStageAndTimeOrderById(String project, String stage, Integer time);
}
