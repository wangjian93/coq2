package com.ivo.coq.costCategory.reworkScrap.service;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCost;

import java.util.List;

/**
 * 重工报废费用服务接口
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCostService {

    /**
     * 获取机种的重工报废费用列表
     * @param project 机种
     * @return List<ReworkScrapCost>
     */
    List<ReworkScrapCost> getReworkScraps(String project);

    List<ReworkScrapCost> getReworkScrapCosts(String project, String stage);

    /**
     * 获取机种指定阶段的重工报废费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return ReworkScrapCost
     */
    ReworkScrapCost getReworkScrapCost(String project, String stage, Integer time);

    /**
     * 创建机种的重工报废费用
     * @param project 机种
     */
    void createReworkScrapCost(String project);
}
