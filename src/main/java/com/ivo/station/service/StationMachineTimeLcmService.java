package com.ivo.station.service;

/**
 * @author wj
 * @version 1.0
 */
public interface StationMachineTimeLcmService {

    /**
     * 计算LCM的站点费用
     */
    void computeLcmStationAmount();


    /**
     * 获取Lcm单片生产费用
     * @param month 月份
     * @param product 成品料号
     * @return
     */
    Double getPerProductAmountLcm(String month, String product);

    /**
     * 获取LCM某站点报废的单片费用
     * @param month 月份
     * @param product 成品料号
     * @param station 站点
     * @return
     */
    Double getPerScrapAmountLcm(String month, String product, String station);

    /**
     * 获取LCM某站点重工的单片费用
     * @param month 月份
     * @param product 成品料号
     * @param station 站点
     * @return
     */
    Double getPerReworkAmountLcm(String month, String product, String station);
}
