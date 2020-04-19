package com.ivo.coq.costCategory.travel.service;

import com.ivo.coq.costCategory.travel.entity.TravelCostDetail;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface TravelCostDetailService {

    /**
     * 根据机种获取差旅费用详情
     * @param project 机种
     * @return List<TravelCostDetail>
     */
    List<TravelCostDetail> getTravelCostDetail(String project);

    /**
     * 根据类型获取机种的差旅费用
     * @param project 机种
     * @param type 成本类型
     * @return
     */
    List<TravelCostDetail> getTravelCostDetail(String project, String type);

    /**
     * 从出差报支单同步差旅信息
     * @param project 机种
     */
    void syncTravelCostDetail(String project);
}
