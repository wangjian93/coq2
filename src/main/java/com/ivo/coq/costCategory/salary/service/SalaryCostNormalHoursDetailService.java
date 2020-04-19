package com.ivo.coq.costCategory.salary.service;

import com.ivo.coq.costCategory.salary.entity.SalaryCostNormalHoursDetail;

import java.util.List;

/**
 * RD标准工时费用服务接口
 * @author wj
 * @version 1.0
 */
public interface SalaryCostNormalHoursDetailService {

    /**
     * 获取机种的RD标准工时费用
     * @param project 机种
     * @return List
     */
    List<SalaryCostNormalHoursDetail> getSalaryCostNormalHoursDetail(String project);

    /**
     * 同步计算RD标准工时费用
     * @param project 机种
     */
    void syncSalaryCostNormalHoursDetail(String project);
}
