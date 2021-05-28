package com.ivo.coq.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 作业费率
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_work_rate")
public class WorkRate extends AutoIncreaseEntityModel {

    /**
     * 成本中心
     */
    private String costCenter;

    private String c_Description;

    /**
     * 作业类型
     */
    private String workType;

    private String w_description;

    /**
     * 货币
     */
    private String currency;

    /**
     * 价格
     */
    private double price;

    private double price2;

    private double price3;

    private String fab;

    /**
     * 月份
     */
    private String month;
}
