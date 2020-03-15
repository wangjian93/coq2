package com.ivo.coq.costCategory.salary.service;

import com.ivo.coq.costCategory.salary.entity.SalaryCost;

import java.util.List;

/**
 * 人员工资费用服务接口
 * @author wj
 * @version 1.0
 */
public interface SalaryCostService {

    /**
     * 获取机种的人员工资费用服务接口
     * @param project 机种
     * @return List<SalaryCost>
     */
    List<SalaryCost> getSalaryCosts(String project);

    /**
     * 获取机种指定阶段的人员工资费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return
     */
    SalaryCost getSalaryCost(String project, String stage, Integer time);

    /**
     * 创建机种的人员工资费用
     * @param project 机种
     */
    void createSalaryCost(String project);
}
