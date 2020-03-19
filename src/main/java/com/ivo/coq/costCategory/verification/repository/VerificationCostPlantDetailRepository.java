package com.ivo.coq.costCategory.verification.repository;

import com.ivo.coq.costCategory.verification.entity.VerificationCostPlantDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface VerificationCostPlantDetailRepository extends JpaRepository<VerificationCostPlantDetail, Long> {


    /**
     * 根据机种筛选
     * @param project 机种
     * @return List
     */
    List<VerificationCostPlantDetail> findByProjectOrderById(String project);

    /**
     * 根据机种、阶段、次数筛选
     * @param project 机种
     * @param stage 阶段
     * @param time 次数
     * @return List
     */
    List<VerificationCostPlantDetail> findByProjectAndStageAndTimeOrderById(String project, String stage, Integer time);
}
