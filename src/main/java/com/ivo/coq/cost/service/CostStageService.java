package com.ivo.coq.cost.service;

import com.ivo.coq.cost.entity.CostStage;

import java.util.List;

/**
 * 机种阶段的成本服务接口
 * @author wj
 * @version 1.0
 */
public interface CostStageService {

    /**
     * 获取机种的阶段成本列表
     * @param project 机种
     * @return List<CostStage>
     */
    List<CostStage> getCostStages(String project);

    /**
     * 创建机种的阶段成本
     * @param project 机种
     */
    void createCostStage(String project);

    /**
     * 计算机种的阶段成本
     * @param project 机种
     */
    void computeCostStage(String project);
}
