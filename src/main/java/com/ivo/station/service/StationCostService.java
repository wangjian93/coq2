package com.ivo.station.service;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCostService {

    /**
     * 获取ARY机种单片的生产费用，经过站点的费用总和
     * @param project 机种
     * @param month 月份
     * @return double
     */
    double getPerProductAmountAry(String month, String project);

    /**
     * 获取CELL机种单片的生产费用，经过站点的费用总和
     * @param project 机种
     * @param month 月份
     * @param prod_id PFCD
     * @return double
     */
    double getPerProductAmountCell(String month, String project, String prod_id);

    /**
     * 获取LCM机种单片的生产费用，经过站点的费用总和
     * @param product 机种的工单对应的product
     * @param month 月份
     * @return double
     */
    double getPerProductAmountLcm(String month, String product);

    /**
     * 获取ARY机种单片在站点报废的费用
     * @param project 机种
     * @param month 月份
     * @param station 站点
     * @return double
     */
    double getPerScrapAmountAry(String month, String project, String station);

    /**
     * 获取Cell机种单片在站点报废的费用
     * @param project 机种
     * @param month 月份
     * @param station 站点
     * @param prod_id PFCD
     * @return double
     */
    double getPerScrapAmountCell(String month, String project, String station, String prod_id);

    /**
     * 获取Lcm机种单片在站点报废的费用
     * @param product 机种的工单对应的product
     * @param month 月份
     * @param station 站点
     * @return double
     */
    double getPerScrapAmountLcm(String month, String product, String station);

    /**
     * 获取ARY机种单片在站点重工的费用
     * @param project 机种
     * @param month 月份
     * @param station 站点
     * @return double
     */
    double getPerReworkAmountAry(String month, String project, String station);

    /**
     * 获取Cell机种单片在站点重工的费用
     * @param project 机种
     * @param month 月份
     * @param station 站点
     * @param prod_id PFCD
     * @return double
     */
    double getPerReworkAmountCell(String month, String project, String station, String prod_id);

    /**
     * 获取Lcm机种单片在站点重工的费用
     * @param product 机种的工单对应的product
     * @param month 月份
     * @param station 站点
     * @return double
     */
    double getPerReworkAmountLcm(String month, String product, String station);
}
