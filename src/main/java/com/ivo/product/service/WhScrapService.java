package com.ivo.product.service;

/**
 * @author wj
 * @version 1.0
 */
public interface WhScrapService {

    /**
     * 同步仓库报废金额数据
     */
    void syncWhScrap();

    /**
     * By月计算仓库报废金额
     * @param month 月份
     */
    void computeWhScrapMonth(String month);
}
