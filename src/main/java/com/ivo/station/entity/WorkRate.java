package com.ivo.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 作业类型价格  （分钟）
 * 来源SAP - KSBT的单位费率
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Work_Rate")
public class WorkRate extends AutoIncreaseEntityModel {

    /**
     * 成本中心
     */
    @Column(name = "cost_Center")
    private String costCenter;

    /**
     * 成本中心名称
     */
    private String cost_name;

    /**
     * 作业类型
     */
    private String work_type;

    /**
     * 作业名称
     */
    private String work_name;

    /**
     * 价格 （每分钟作业的费用）
     */
    private double price;

    /**
     * 币别
     */
    private String currency;


}
