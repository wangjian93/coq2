package com.ivo.product.service;

import com.ivo.product.entity.WoClose;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface WoCloseService {

    /**
     * 同步超耗数量数据
     */
    void syncWoClose(String fromDate, String toDate);

    void syncWoClose(String month);

    /**
     * 同步LCM料号的价格
     * @param month 月份
     */
    void syncMatnrPrice(String month);

    /**
     * 获取Lcm1的WoClose
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    List<WoClose> getWoCloseLcm1(Date fromDate, Date toDate);

    /**
     * 获取Lcm2的WoClose
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    List<WoClose> getWoCloseLcm2(Date fromDate, Date toDate);

    /**
     * by月计算LCM量产超耗费用
     * @param month 月份
     */
    void computeWoCloseMonth(String month);
}
