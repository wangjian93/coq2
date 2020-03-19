package com.ivo.coq.costCategory.jig.repository;

import com.ivo.coq.costCategory.jig.entity.JigCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface JigCostRepository extends JpaRepository<JigCost, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<JigCost>
     */
    List<JigCost> findByProjectOrderById(String project);

    /**
     * 根据机种、阶段筛选
     * @param project 机种
     * @param stage 阶段
     * @return List<JigCost>
     */
    List<JigCost> findByProjectAndStageOrderById(String project, String stage);

    /**
     * 根据机种、阶段、阶段次数筛选
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return JigCost
     */
    JigCost findByProjectAndStageAndTime(String project, String stage, Integer time);
}
