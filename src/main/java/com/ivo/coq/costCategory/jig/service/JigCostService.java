package com.ivo.coq.costCategory.jig.service;

import com.ivo.coq.costCategory.jig.entity.JigCost;

import java.util.List;

/**
 * 治工具费用服务接口
 * @author wj
 * @version 1.0
 */
public interface JigCostService {

    /**
     * 获取机种的治工具费用列表
     * @param project 机种
     * @return List<JigCost>
     */
    List<JigCost> getJigCosts(String project);

    List<JigCost> getJigCosts(String project, String stage);

    /**
     * 获取机种指定阶段次数的治工具费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return JigCost
     */
    JigCost getJigCost(String project, String stage, Integer time);

    /**
     * 创建机种的治工具费用
     * @param project 机种
     */
    void createJigCost(String project);
}
