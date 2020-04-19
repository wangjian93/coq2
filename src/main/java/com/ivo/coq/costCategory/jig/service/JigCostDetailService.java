package com.ivo.coq.costCategory.jig.service;

import com.ivo.coq.costCategory.jig.entity.JigCostDetail;

import java.util.List;

/**
 * 治工具费用详细服务接口
 * @author wj
 * @version 1.0
 */
public interface JigCostDetailService {

    /**
     * 根据机种获取治工具费用详细
     * @param project 机种
     * @return List<JigCostDetail>
     */
    List<JigCostDetail> getJigCostDetail(String project);

    /**
     * 根据机种、阶段、次数获取治工具费用详细
     * @param project 机种
     * @param stage 阶段
     * @param time 次数
     * @return List<JigCostDetail>
     */
    List<JigCostDetail> getJigCostDetail(String project, String stage, Integer time);

    /**
     * 从BM同步治工具费用
     * @param project 机种
     */
    void syncJigCostDetail(String project);
}
