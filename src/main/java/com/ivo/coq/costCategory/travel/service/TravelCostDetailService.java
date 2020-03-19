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
}
