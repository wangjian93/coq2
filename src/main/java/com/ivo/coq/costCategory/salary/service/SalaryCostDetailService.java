package com.ivo.coq.costCategory.salary.service;

import com.ivo.coq.costCategory.salary.entity.SalaryCostDetail;

import java.util.List;

/**
 * 薪资费用详细服务接口
 * @author wj
 * @version 1.0
 */
public interface SalaryCostDetailService {

    /**
     * 获取机种的人员薪资费用
     * @param project 机种
     * @return
     */
    List<SalaryCostDetail> getSalaryCostDetail(String project);

    /**
     * 同步计算人员薪资费用
     * @param project 机种
     */
    void syncSalaryCostDetail(String project);
}
