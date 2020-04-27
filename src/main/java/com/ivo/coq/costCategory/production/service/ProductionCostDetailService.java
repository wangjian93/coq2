package com.ivo.coq.costCategory.production.service;

import com.ivo.coq.costCategory.production.entity.ProductionCostDetail;

import java.util.List;

/**
 * 生产费用详细服务接口
 * @author wj
 * @version 1.0
 */
public interface ProductionCostDetailService {

    /**
     * 根据机种获取生产费用详细列表
     * @param project 机种
     * @return List<ProductionCostDetail>
     */
    List<ProductionCostDetail> getProductionCostDetail(String project);

    List<ProductionCostDetail> getProductionCostDetail(String project, String stage, Integer time);

    /**
     * 同步创建生产费用详细
     * @param project 机种
     */
    void syncProductionCostDetail(String project);

    /**
     * 计算生产费用详细
     * @param project 机种
     */
    void computeProductionCostDetail(String project);
}
