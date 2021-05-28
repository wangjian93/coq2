package com.ivo.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ARY的站点Cycle Time  （小时）
 * 来源：(MDC-2944) Array 各站cycle time报表
 *
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Station_CycleTime_Ary")
public class StationCycleTimeAry extends AutoIncreaseEntityModel {

    /**
     * 月份
     */
    private String month;

    /**
     * 机种
     */
    private String project;

    /**
     * 站点
     */
    private String station;

    /**
     * 站点描述
     */
    private String stationDes;

    /**
     * 机台
     */
    private String eqptId;

    /**
     * pep数
     */
    private String pep_cnt;

    /**
     * 过账大板数
     */
    private double sht_cnt;

    private double b_cnt;

    private double s_cnt;

    private double r_cnt;

    private double proc_cnt;

    /**
     * 财务成本工时 （生产一片用多少小时）
     *
     * */

    private double b_time;

    private double s_time;

    private double r_time;

    private double m_time;


    /**
     * 成本中心 （由机台关联）
     */
    private String costCenter;

    // 单位费率 = 成本中心作业的每分钟价格
    // 生产费用/报废费用 （单片）= S_CNT数量对应的S财务成本工时*60*单位费率+M_CNT数量对应的M财务成本工时*60*单位费率
    // 重工费用 （单片）= R_CNT数量对应的R财务成本工时*60*单位费率+B_CNT数量对应的B财务成本工时*60*单位费率

    private double workRate;

    /**
     * 一片经过该站点需要的费用
     */
    private double amount;

    /**
     * 在一片该站点重工需要的费用
     */
    private double reworkAmount;

    /**
     * 备注
     */
    private String memo;

}
