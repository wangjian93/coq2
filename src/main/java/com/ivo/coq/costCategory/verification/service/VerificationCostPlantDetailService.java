package com.ivo.coq.costCategory.verification.service;

import com.ivo.coq.costCategory.verification.entity.VerificationCostPlantDetail;

import java.util.List;

/**
 * 验证费用--厂内部分服务接口
 * @author wj
 * @version 1.0
 */
public interface VerificationCostPlantDetailService {

    /**
     * 根据机种获取厂内验证费用
     * @param project 机种
     * @return List
     */
    List<VerificationCostPlantDetail> getVerificationCostPlantDetail(String project);

    /**
     * 根据机种、阶段、次数获取厂内验证费用
     * @param project 机种
     * @param stage 阶段
     * @param time 次数
     * @return List
     */
    List<VerificationCostPlantDetail> getVerificationCostPlantDetail(String project, String stage, Integer time);
}
