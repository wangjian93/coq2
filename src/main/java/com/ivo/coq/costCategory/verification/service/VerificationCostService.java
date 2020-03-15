package com.ivo.coq.costCategory.verification.service;

import com.ivo.coq.costCategory.verification.entity.VerificationCost;

import java.util.List;

/**
 * 验证费用服务接口
 * @author wj
 * @version 1.0
 */
public interface VerificationCostService {

    /**
     * 获取机种的验证费用列表
     * @param project 机种
     * @return  List<VerificationCost>
     */
    List<VerificationCost> getVerificationCosts(String project);

    List<VerificationCost> getVerificationCosts(String project, String stage);

    /**
     * 获取机种指定阶段次数的验证费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return VerificationCost
     */
    VerificationCost getVerificationCost(String project, String stage, Integer time);

    /**
     * 创建机种的验证费用
     * @param project 机种
     */
    void crateVerificationCost(String project);
}
