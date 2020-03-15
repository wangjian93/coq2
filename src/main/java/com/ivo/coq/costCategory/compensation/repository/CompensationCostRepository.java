package com.ivo.coq.costCategory.compensation.repository;

import com.ivo.coq.costCategory.compensation.entity.CompensationCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface CompensationCostRepository extends JpaRepository<CompensationCost, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<CompensationCost>
     */
    List<CompensationCost> findByProject(String project);

    /**
     * 根据机种、阶段筛选
     * @param project 机种
     * @param stage 阶段
     * @return List<CompensationCost>
     */
    List<CompensationCost> findByProjectAndStage(String project, String stage);

    /**
     * 根据机种、阶段、阶段次数
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return CompensationCost
     */
    CompensationCost findFirstByProjectAndStageAndTime(String project, String stage, Integer time);
}
