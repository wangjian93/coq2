package com.ivo.coq.cost.service;

import com.ivo.coq.cost.entity.Cost;

import java.util.List;

/**
 * 机种成本服务接口
 * @author wj
 * @version 1.0
 */
public interface CostService {

    /**
     * 获取机种的成本数据
     * @param project 机种
     * @return Cost
     */
    Cost getCost(String project);

    /**
     * 获取所有机种的成本
     * @return List<Cost>
     */
    List<Cost> getCosts();

    /**
     * 创建Cost
     * @param project 机种
     */
    void createCost(String project);

    /**
     * 计算Cost
     * @param project 机种
     */
    void computeCost(String project);
}
