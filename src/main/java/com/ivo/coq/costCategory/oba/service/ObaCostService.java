package com.ivo.coq.costCategory.oba.service;

import com.ivo.coq.costCategory.oba.entity.ObaCost;

import java.util.List;

/**
 * OBA费用服务接口
 * @author wj
 * @version 1.0
 */
public interface ObaCostService {

    /**
     * 获取机种的OBA费用列表
     * @param project 机种
     * @return List<ObaCost>
     */
    List<ObaCost> getObaCosts(String project);

    List<ObaCost> getObaCosts(String project, String stage);

    /**
     * 获取机种指定阶段次数的OBA费用
     * @param project 机种
     * @param stage 阶段
     * @param time 阶段次数
     * @return ObaCost
     */
    ObaCost getObaCost(String project, String stage, Integer time);

    /**
     * 创建机种的OBA费用
     * @param project 机种
     */
    void createObaCost(String project);
}
