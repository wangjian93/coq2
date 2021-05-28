package com.ivo.station.service;

import com.ivo.station.entity.WorkRate;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface WorkRateService {

    /**
     * 获取成本中心的作业费率
     * @param costCenter 成本中心
     * @return
     */
    List<WorkRate> getWorkRate(String costCenter);

    /**
     * 获取成本中心的作业费率
     * @param costCenter 成本中心
     * @return
     */
    double getTotalPrice(String costCenter);

    /**
     * 获取人工费率
     * @param costCenter
     * @return
     */
    double getM_price(String costCenter);

    /**
     * 获取其他费率
     * @param costCenter
     * @return
     */
    double getOther_price(String costCenter);
}
