package com.ivo.coq.costCategory.directMaterial.service;

import com.ivo.coq.costCategory.directMaterial.entity.MaterialCostDetail;

import java.util.List;

/**
 * 厂内直材费用服务接口
 * @author wj
 * @version 1.0
 */
public interface MaterialCostDetailService {

    /**
     * 根据机种获取获取材料费用明细列表
     * @param project 机种
     * @return List<MaterialCostDetail>
     */
    List<MaterialCostDetail> getMaterialCostDetails(String project);

    /**
     * 根据机种、阶段、阶段次数获取材料费用明细列表
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return List<MaterialCostDetail>
     */
    List<MaterialCostDetail> getMaterialCostDetails(String project, String stage, Integer time);

    /**
     * 创建厂内直材费用
     * @param project 机种
     */
    void createMaterialCostDetail(String project);

    /**
     * 计算厂内直材费用
     * @param project 机种
     */
    void computeMaterialCostDetail(String project);

    /**
     * 同步Array厂的材料单片费用
     * @param project 机种
     */
    void syncArrayProductAmount(String project);

    /**
     * 同步LCM厂的工单费用
     * @param project 机种
     */
    void syncLcmWoAmount(String project);

    /**
     * 同步CELL厂的料号单价
     * @param project 机种
     */
    void syncCellMaterialAmount(String project);
}
