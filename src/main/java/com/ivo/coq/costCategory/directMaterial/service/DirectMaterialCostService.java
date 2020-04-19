package com.ivo.coq.costCategory.directMaterial.service;

import com.ivo.coq.costCategory.directMaterial.entity.DirectMaterialCost;

import java.util.List;

/**
 * 直接材料费用服务接口
 * @author wj
 * @version 1.0
 */
public interface DirectMaterialCostService {

    /**
     * 获取机种的直接材料费用
     * @param project 机种
     * @return List<DirectMaterialCost>
     */
    List<DirectMaterialCost> getDirectMaterialCosts(String project);

    /**
     * 获取机种阶段的直接材料费用
     * @param project 机种
     * @param stage 阶段
     * @return
     */
    List<DirectMaterialCost> getDirectMaterialCosts(String project, String stage);

    /**
     * 获取机种指定阶段次数的直接材料费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return DirectMaterialCost
     */
    DirectMaterialCost getDirectMaterialCost(String project, String stage, Integer time);

    /**
     * 创建机种的直接材料费用
     * @param project 机种
     */
    void createDirectMaterialCost(String project);

    /**
     * 计算机种的直接材料费用
     * @param project 机种
     */
    void computeDirectMaterialCost(String project);
 }
