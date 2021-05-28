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
@Entity(name = "coq_Station_Cost_Cell")
public class StationCostCell extends AutoIncreaseEntityModel {

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
     * 主站点大板数
     */
    private double mainQty;

    /**
     * 主站点小板数
     */
    private double mainQty_;

    /**
     * Rework站点大板数
     */
    private double reworkQty;

    /**
     * Rework站点小板数
     */
    private double reworkQty_;

    /**
     * 检查站点大板数
     */
    private double checkQty;

    /**
     * 检查站点小板数
     */
    private double checkQty_;

    /**
     * 主站点工时
     */
    private double mainWorkHours;

    /**
     * Rework站点工时
     */
    private double reworkHours;

    /**
     * 检查站点工时
     */
    private double checkWorkHours;


    /**
     * 作业的单位费率
     */
    private double workRate;

    /**
     * 大板切片数
     */
    private double cut;

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
