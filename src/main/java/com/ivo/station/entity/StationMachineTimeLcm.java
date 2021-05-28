package com.ivo.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * LCM成品机台加工时间  （分钟）
 * 来源SAP表ZCO_FG_OPE_WO
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "station_machine_time_lcm")
public class StationMachineTimeLcm extends AutoIncreaseEntityModel {

    Date mes_cut_date;

    /**
     * 成品料号
     */
    String matnr;

    /**
     * 图程
     */
    String mes_route;

    /**
     * 工作中心
     */
    String  work_center;

    /**
     * 基础数量
     */
    double base_qty;

    /**
     * 机器工时 （生产一片用多少分钟）
     */
    double time_p;

    /**
     * 人工工时 （生产一片用多少分钟）
     */
    double time_m;

    /**
     * 时间单位
     */
    String unit;


    /**
     * 月份
     */
    String month;

    /**
     * 站点（由工作中新截取后四码）
     */
    private String station;

    /**
     * 成本中心 （由工作中心关联）
     */
    private String costCenter;

    // 单位费率 = 成本中心作业的每分钟价格
    //生产/报废费用（单片）=人工工时/基础数量*人工费率+机器工时/基础数量*其他费率
    //重工费用（单片）=0

    private double workRate_m;
    private double workRate_other;

    /**
     * 一片经过该站点需要的费用
     */
    private double amount;

    /**
     * 在一片该站点重工需要的费用
     */
    private double reworkAmount=0;

    /**
     * 备注
     */
    private String memo;
}
