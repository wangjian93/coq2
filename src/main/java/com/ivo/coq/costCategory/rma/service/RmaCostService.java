package com.ivo.coq.costCategory.rma.service;

import com.ivo.coq.costCategory.rma.entity.RmaCost;

import java.util.List;

/**
 * RMA费用服务接口
 * @author wj
 * @version 1.0
 */
public interface RmaCostService {

    /**
     * 获取机种的RMA费用列表
     * @param project 机种
     * @return List<RmaCost>
     */
    List<RmaCost> getRmaCosts(String project);

    List<RmaCost> getRmaCosts(String project, String stage);

    /**
     * 获取机种指定阶段的RMA费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return RmaCost
     */
    RmaCost getRmaCost(String project, String stage, Integer time);

    /**
     * 创建机种的RMA费用
     * @param project 机种
     */
    void createRmaCost(String project);
}
