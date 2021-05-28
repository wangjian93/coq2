package com.ivo.coq.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_Station_Cost_Lcm")
public class StationCostLcm extends AutoIncreaseEntityModel {

    /**
     * 月份
     */
    private String month;

    /**
     * 机种
     */
    private String project;

    private String wo;

    private String station;

    /**
     * product_id
     */
    private String product;

    /**
     * 途程
     */
    private String way;

    /**
     * 工作中心
     */
    private String workCenter;

    private Date date;


    /**
     * 基础数量
     */
    private double baseQty;

    /**
     * 人工工时
     */
    private double manWorkHours;

    /**
     * 机器工时
     */
    private double machineWorkHours;

    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 人工费率
     */
    private double manWorkRate;

    /**
     * 机器费率
     */
    private double machineWorkRate;

    /**
     * 单片生产、报废费用
     */
    private double perProductCost;

}
