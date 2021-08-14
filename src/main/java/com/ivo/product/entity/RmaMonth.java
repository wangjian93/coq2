package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 量产品的RMA费用
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Rma_Month")
public class RmaMonth extends AutoIncreaseEntityModel {

    /**
     * 月份
     */
    private String month;

    /**
     *  LCM1 LCM2  CELL
     */
    private String fab;

    /**
     * 费用
     */
    private double amount;
}
