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
     * 计算量产报废损失(外包)
     * @param month 月份
     */
    void computeWhScrapMonth(String month);

    /**
     * 计算量产RMA费用
     * @param month
     */
    void computeRmaMonth(String month);
}
