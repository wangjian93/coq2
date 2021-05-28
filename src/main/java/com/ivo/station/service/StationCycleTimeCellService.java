package com.ivo.station.service;

import java.util.Date;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCycleTimeCellService {

    /**
     * 按月同步CELL cycle time的数据
     * @param month
     */
    void syncStationCycleTimeCell(String month);

    /**
     * 从MDC同步数据
     * @param fromDate 开始日期
     * @param toDate 结束日期
     */
    void syncStationCycleTimeCell(Date fromDate, Date toDate, String month);

    /**
     * 计算Cell的站点费用
     * @param month 月份
     */
    void computeCellStationAmount(String month);


    /**
     * 获取CELL机种单片生产费用
     * @param month 月份
     * @param project 机种
     * @param prod_id PFCD
     * @return
     */
    Double getPerProductAmountCell(String month, String project, String prod_id);

    /**
     * 获取CELL机种在某站点报废的单片费用
     * @param month 月份
     * @param project 机种
     * @param station 站点
     * @param prod_id PFCD
     * @return
     */
    Double getPerScrapAmountCell(String month, String project, String station, String prod_id);

    /**
     * 获取CELL机种在某站点重工的单片费用
     * @param month 月份
     * @param project 机种
     * @param station 站点
     * @param prod_id PFCD
     * @return
     */
    Double getPerReworkAmountCell(String month, String project, String station, String prod_id);
}
