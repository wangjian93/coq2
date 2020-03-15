package com.ivo.coq.costCategory.directMaterial.repository;

import com.ivo.coq.costCategory.directMaterial.entity.DirectMaterialCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface DirectMaterialCostRepository extends JpaRepository<DirectMaterialCost, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<DirectMaterialCost>
     */
    List<DirectMaterialCost> findByProject(String project);

    /**
     * 根据接种、阶段筛选
     * @param project 机种
     * @param stage 阶段
     * @return List<DirectMaterialCost>
     */
    List<DirectMaterialCost> findByProjectAndStage(String project, String stage);

    /**
     * 根据机种、阶段、阶段次数筛选
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return DirectMaterialCost
     */
    DirectMaterialCost findFirstByProjectAndStageAndTime(String project, String stage, Integer time);
}
