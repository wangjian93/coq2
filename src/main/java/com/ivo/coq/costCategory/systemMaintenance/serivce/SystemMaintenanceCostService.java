package com.ivo.coq.costCategory.systemMaintenance.serivce;

import com.ivo.coq.costCategory.systemMaintenance.entity.SystemMaintenanceCost;

import java.util.List;

/**
 * 系统维护折旧费用服务接口
 * @author wj
 * @version 1.0
 */
public interface SystemMaintenanceCostService {

    /**
     * 获取机种的系统维护折旧费用列表
     * @param project 机种
     * @return List<SystemMaintenanceCost>
     */
    List<SystemMaintenanceCost> getSystemMaintenanceCosts(String project);

    /**
     * 获取机种指定阶段的系统折旧维护费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return SystemMaintenanceCost
     */
    SystemMaintenanceCost getSystemMaintenanceCost(String project, String stage, Integer time);

    /**
     * 创建机种的系统维护折旧费
     * @param project 机种
     */
    void createSystemMaintenanceCos(String project);
}
