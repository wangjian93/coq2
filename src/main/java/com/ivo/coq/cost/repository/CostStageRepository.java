package com.ivo.coq.cost.repository;

import com.ivo.coq.cost.entity.CostStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface CostStageRepository extends JpaRepository<CostStage, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<CostStage>
     */
    List<CostStage> findByProject(String project);
}
