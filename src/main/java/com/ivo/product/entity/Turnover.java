package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "turnover")
@Setter
@Getter
public class Turnover extends AutoIncreaseEntityModel {

    /**
     * 季度
     */
    private String season;

    /**
     * 营业额
     */
    private double amount;
}
