package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 重工报废费用By月
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Rework_Scrap_Month")
public class ReworkScrapMonth extends AutoIncreaseEntityModel {
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
