package com.ivo.coq.costCategory.travel.service;

import com.ivo.coq.costCategory.travel.entity.TravelCost;

import java.util.List;

/**
 * 差旅交际费用服务接口
 * @author wj
 * @version 1.0
 */
public interface TravelCostService {

    /**
     * 获取机种的差旅交际费用列表
     * @param project 机种
     * @return
     */
    List<TravelCost> getTravelCosts(String project);

    /**
     * 获取机种指定阶段次数的差旅交际费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return TravelCost
     */
    TravelCost getTravelCost(String project, String stage, Integer time);

    /**
     * 创建机种的差旅交际费用
     * @param project 机种
     */
    void createTravelCost(String project);
}
