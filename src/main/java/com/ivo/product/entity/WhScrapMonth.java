package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 仓库报废金额By费用
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Wh_Scrap_Month")
public class WhScrapMonth extends AutoIncreaseEntityModel {

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
