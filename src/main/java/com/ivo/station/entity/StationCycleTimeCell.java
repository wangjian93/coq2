package com.ivo.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CELL的站点Cycle Time  (天）
 * 来源：(MDC-2945 CELL) 各站cycle time报表
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Station_Cycle_Time_Cell")
public class StationCycleTimeCell extends AutoIncreaseEntityModel {

    /**
     * 月份
     */
    private String month;

    /**
     * 机种
     */
    private String project;

    /**
     * 分类
     */
    private String prodId;

    /**
     * 是否薄化
     */
    private String glass_thin;

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


    /** 主站点大板数 **/
    private double m_cnt;
    /** 主站点小板数 **/
    private double m_cnt_s;

    /** rework站点大板数 **/
    private double r_cnt;
    /** rework站点小板数 **/
    private double r_cnt_s;

    /** 检查站点大板数 **/
    private double s_cnt;
    /** 检查站点小板数 **/
    private double s_cnt_s;

    // CT(不含等待) （生产一片用多少天）
    /**
     * 主站点工时(天/24小时)
     */
    private double m_time;

    /**
     * rework站点工时(天/24小时)
     */
    private double r_time;

    /**
     * 检查站点工时(天/24小时)
     */
    private double s_time;

    /**
     * 成本中心 （由机台关联）
     */
    private String costCenter;

    // 单位费率 = 成本中心作业的每分钟价格
    // 生产/报废费用（单片）=主站点对应的主站点CT（不含等待）*24*60*单位费率+检查站点对应的检查站点CT（不含等待）*24*60*单位费率
    // 重工费用（单片）=Rework站点对应的Rework站点CT（不含等待）*24*60*单位费率

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
