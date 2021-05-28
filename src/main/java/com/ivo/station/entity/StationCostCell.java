package com.ivo.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CELL站点的费用，单片经过站点的费用
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Station_Cost_Cell")
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

    /**
     * 分类
     */
    private String prod_id;

    /**
     * 经过该站点需要的费用
     */
    private double amount;

    /**
     * 在该站点重工需要的费用
     */
    private double reworkAmount;

    /**
     * 备注
     */
    private String memo;
}
