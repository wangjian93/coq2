package com.ivo.coq.costCategory.salary.repository;

import com.ivo.coq.costCategory.salary.entity.SalaryCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface SalaryCostRepository extends JpaRepository<SalaryCost, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<SalaryCost>
     */
    List<SalaryCost> findByProjectOrderById(String project);

    /**
     * 根据机种、阶段、阶段次数筛选
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return SalaryCost
     */
    SalaryCost findFirstByProjectAndStageAndTime(String project, String stage, Integer time);
}
