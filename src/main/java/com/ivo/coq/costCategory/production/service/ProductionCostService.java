package com.ivo.coq.costCategory.production.service;

import com.ivo.coq.costCategory.production.entity.ProductionCost;

import java.util.List;

/**
 * 生产费用服务接口
 * @author wj
 * @version 1.0
 */
public interface ProductionCostService {

    /**
     * 获取机种的生产费用服务接口
     * @param project 机种
     * @return List<ProductionCost>
     */
    List<ProductionCost> getProductionCosts(String project);

    List<ProductionCost> getProductionCosts(String project, String stage);

    /**
     * 获取机种指定阶段的生产费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return ProductionCost
     */
    ProductionCost getProductionCost(String project, String stage, Integer time);

    /**
     * 创建机种的生产费用
     * @param project 机种
     */
    void createProductionCost(String project);

    /**
     * 计算机种的生产费用
     * @param project 机种
     */
    void computeProductionCost(String project);
 }
