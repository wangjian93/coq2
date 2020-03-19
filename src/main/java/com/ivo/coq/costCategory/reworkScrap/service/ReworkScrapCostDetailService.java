package com.ivo.coq.costCategory.reworkScrap.service;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostDetail;

import java.util.List;

/**
 * 重工报废费用详细服务接口
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCostDetailService {

    /**
     * 根据机种获取重工报废费用详细
     * @param project 机种
     * @return List<ReworkScrapCostDetail>
     */
    List<ReworkScrapCostDetail> getReworkScrapCostDetail(String project);
}
