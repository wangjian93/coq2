package com.ivo.coq.costCategory.compensation.service;

import com.ivo.coq.costCategory.compensation.entity.CompensationCost;

import java.util.List;

/**
 * 赔偿费用服务接口
 * @author wj
 * @version 1.0
 */
public interface CompensationCostSerivce {

    /**
     * 获取机种的赔偿费用列表
     * @param project 机种
     * @return List<CompensationCost>
     */
    List<CompensationCost> getCompensationCosts(String project);

    List<CompensationCost> getCompensationCosts(String project, String stage);

    /**
     * 获取机种指定阶段次数的赔偿费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return CompensationCost
     */
    CompensationCost getCompensationCost(String project, String stage, Integer time);

    /**
     * 创建机种的赔偿费用
     * @param project 机种
     */
    void createCompensationCost(String project);
}
