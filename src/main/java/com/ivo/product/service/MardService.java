package com.ivo.product.service;

import com.ivo.product.entity.Mard;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface MardService {

    /**
     * 同步LCM入库数量
     * @param fromDate 开始日期
     * @param toDate 结束日期
     */
    void syncMard(String fromDate, String toDate);

    void syncMard(String month);

    /**
     * 同步LCM1料号的价格
     * @param month 月份
     */
    void syncMatnrPrice(String month);

    /**
     * 获取LCM1入库数据
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    List<Mard> getMardLcm1(Date fromDate, Date toDate);

    /**
     * 获取LCM2入库数据
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    List<Mard> getMardLcm2(Date fromDate, Date toDate);

    /**
     * by月计算入库的总成本
     * @param month 月份
     */
    void computeMardMonth(String month);
}
