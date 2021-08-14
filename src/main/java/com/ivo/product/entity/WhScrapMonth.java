package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 量产品报废损失(外包)
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
     *  LCM1 LCM2  ARRAY CELL
     */
    private String fab;

    /**
     * 费用
     */
    private double amount;
}
