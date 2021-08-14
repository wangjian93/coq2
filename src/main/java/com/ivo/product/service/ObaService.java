package com.ivo.product.service;

/**
 * @author wj
 * @version 1.0
 */
public interface ObaService {

    /**
     * 同步OBA数据
     */
    void syncOba();

    /**
     * 同步OBA数据的工厂
     */
    void syncFab();


    /**
     * By月计算量产品的OBA费用
     * @param month 月份
     */
    void computeObaMonth(String month);
}
