package com.ivo.coq.costCategory.verification.repository;

import com.ivo.coq.costCategory.verification.entity.VerificationCostBmDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface VerificationCostBmDetailRepository extends JpaRepository<VerificationCostBmDetail, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List
     */
    List<VerificationCostBmDetail> findByProject(String project);

    /**
     * 根据机种、阶段、次数筛选
     * @param project 机种
     * @param stage 阶段
     * @param time 次数
     * @return List
     */
    List<VerificationCostBmDetail> findByProjectAndStageAndTime(String project, String stage, Integer time);
}
