package com.ivo.coq.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_Station_Cost_Ary")
public class StationCostAry extends AutoIncreaseEntityModel {

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

    private String stationName;

    /**
     * 机台
     */
    private String eq;

    /**
     * 过账大板数
     */
    private double postBigBoard;

    private double b_cnt;
    private double s_cnt;
    private double r_cnt;
    private double m_cnt;

    private double b_workHours;
    private double s_workHours;
    private double r_workHours;
    private double m_workHours;


    /**
     * 作业的单位费率
     */
    private double workRate;

    /**
     * 单片重工费用
     */
    private double perReworkCost;

    private double perReworkCost_;

    /**
     * 单片生产、报废费用
     */
    private double perProductCost;

    private String memo;
}
