package com.ivo.station.service;

import java.util.Date;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCycleTimeAryService {

    /**
     * 同步MDC中ary cycle time数据至临时表
     * @param fromDate 开始日期
     * @param toDate 结束日期
     */
    void syncStationCycleTime(Date fromDate, Date toDate);

    /**
     * 合计ary cycle time临时表数据
     */
    void sumStationCycleTime(String month);

    /**
     * 按月同步ary cycle time的数据
     * @param month 月份
     */
    void syncStationCycleTime(String month);

    /**
     * 计算ARY的站点费用
     * @param month 月份
     */
    void computeAryStationAmount(String month);

    /**
     * 获取ARY机种单片生产费用
     * @param month 月份
     * @param project 机种
     * @return
     */
    Double getPerProductAmountAry(String month, String project);

    /**
     * 获取ARY机种在某站点报废的单片费用
     * @param month 月份
     * @param project 机种
     * @param station 站点
     * @return
     */
    Double getPerScrapAmount(String month, String project, String station);

    /**
     * 获取ARY机种在某站点重工的单片费用
     * @param month 月份
     * @param project 机种
     * @param station 站点
     * @return
     */
    Double getPerReworkAmountAry(String month, String project, String station);
    
}
