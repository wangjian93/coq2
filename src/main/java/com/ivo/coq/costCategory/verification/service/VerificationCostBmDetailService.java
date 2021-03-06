package com.ivo.coq.costCategory.verification.service;

import com.ivo.coq.costCategory.verification.entity.VerificationCostBmDetail;

import java.util.List;

/**
 * 验证费用--BM服务接口
 * @author wj
 * @version 1.0
 */
public interface VerificationCostBmDetailService {

    /**
     * 根据机种获取BM验证费用
     * @param project 机种
     * @return List<VerificationCostBmDetail>
     */
    List<VerificationCostBmDetail> getVerificationCostBmDetail(String project);

    /**
     * 根据机种、阶段、次数获取BM验证费用
     * @param project 机种
     * @param stage 阶段
     * @param time 次数
     * @return List<VerificationCostBmDetail>
     */
    List<VerificationCostBmDetail> getVerificationCostBmDetail(String project, String stage, Integer time);

    /**
     * 同步获取机种的BM验证费用
     * @param project 机种
     */
    void syncVerificationCostBmDetail(String project);
}
