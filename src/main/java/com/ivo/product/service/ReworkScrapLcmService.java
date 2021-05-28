package com.ivo.product.service;

import java.util.Date;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapLcmService {

    void syncReworkScrap(String month);

    /**
     * 同步LCM1的生产重工报废数据
     * @param fromDate 开始日期
     * @param toDate 结束日期
     */
    void syncReworkScrapLcm1(String fromDate, String toDate);

    /**
     * 同步LCM2的生产重工报废数据
     * @param fromDate 开始日期
     * @param toDate 结束日期
     */
    void syncReworkScrapLcm2(String fromDate, String toDate);

    void syncPerReworkScrapAmountLcm(String month);

    /**
     * 同步LCM1经过站点的单片重工/报废费用
     * @param fromDate 开始日期
     * @param toDate 结束日期
     */
    void syncPerReworkScrapAmountLcm1(Date fromDate, Date toDate);

    /**
     * 同步LCM2经过站点的单片重工/报废费用
     * @param fromDate 开始日期
     * @param toDate 结束日期
     */
    void syncPerReworkScrapAmountLcm2(Date fromDate, Date toDate);


    /**
     * By月计算量产品的重工报废费用
     */
    void computeReworkScrapMonth(String month);
}
