package com.ivo.coq.costCategory.directMaterial.service;

import com.ivo.coq.costCategory.directMaterial.entity.ShipmentCostDetail;

import java.util.List;

/**
 * 出货费用服务接口
 * @author wj
 * @version 1.0
 */
public interface ShipmentCostDetailService {

    /**
     * 获取机种的出货费用
     * @param project 机种
     * @return
     */
    List<ShipmentCostDetail> getShipmentCostDetail(String project);

    /**
     * 创建出货费用
     * @param project 机种
     */
    void createShipmentCostDetail(String project);

    /**
     * 计算出货费用
     * @param project 机种
     */
    void computeShipmentCostDetail(String project);
}
