package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LCM量产工单超耗费用By月
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Wo_Close_Month")
public class WoCloseMonth extends AutoIncreaseEntityModel {

    /**
     * 月份
     */
    private String month;

    /**
     *  LCM1 LCM2
     */
    private String fab;

    /**
     * 费用
     */
    private double amount;
}
