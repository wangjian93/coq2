package com.ivo.coq.costCategory.directMaterial.service;

import com.ivo.coq.costCategory.directMaterial.entity.OutsourcingThinningCostDetail;

import java.util.List;

/**
 * 外包薄化费用服务接口
 * @author wj
 * @version 1.0
 */
public interface OutsourcingThinningCostDetailService {

    /**
     * 根据机种获取外包薄化费用列表
     * @param project 机种
     * @return List<OutsourcingThinningCostDetail>
     */
    List<OutsourcingThinningCostDetail> getOutsourcingThinningCostDetails(String project);

    /**
     * 根据机种、阶段、阶段次数获取外包薄化费用列表
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return List<OutsourcingThinningCostDetail>
     */
    List<OutsourcingThinningCostDetail> getOutsourcingThinningCostDetails(String project, String stage, Integer time);

    /**
     * 同步获取机种外包薄化费用
     * @param project 机种
     */
    void syncOutsourcingThinningCostDetail(String project);
}
