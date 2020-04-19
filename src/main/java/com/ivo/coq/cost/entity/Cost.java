package com.ivo.coq.cost.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机种成本
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_cost")
@Setter
@Getter
public class Cost extends AutoIncreaseEntityModel {

    /**
     * 机种名
     */
    private String project;

    /**
     * 预防费用
     */
    private Double preventionCost;

    /**
     * 鉴定费用
     */
    private Double identityCost;

    /**
     * 内损费用
     */
    private Double inLossCost;

    /**
     * 外损费用
     */
    private Double outLossCost;

    /**
     * 必要费用
     */
    private Double necessaryCost;

    /**
     * 多余费用
     */
    private Double redundantCost;

    public Cost() {}

    public Cost(String project) {
        this.project = project;
    }
}
