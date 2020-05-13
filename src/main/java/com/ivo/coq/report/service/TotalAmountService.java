package com.ivo.coq.report.service;

import com.ivo.coq.report.entity.TotalAmount;

/**
 * @author wj
 * @version 1.0
 */
public interface TotalAmountService {

    /**
     * 同步各厂的总制造成本
     */
    void syncTotalAmount();

    /**
     * 获取厂月份的中制造成本
     * @param plant 厂
     * @param mon 月份
     * @return TotalAmount
     */
    TotalAmount getTotalAmount(String plant, String mon);
}
