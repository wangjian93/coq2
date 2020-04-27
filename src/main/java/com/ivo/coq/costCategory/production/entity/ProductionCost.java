package com.ivo.coq.costCategory.production.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 生产费用
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_cost_production")
public class ProductionCost extends AutoIncreaseEntityModel {

    /**
     * 机种名
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 阶段次数
     */
    private Integer time;

    /**
     * 生产费用
     */
    private Double amount;

    public ProductionCost() {}
    public ProductionCost(String project, String stage, Integer time) {
        this.project = project;
        this.stage = stage;
        this.time = time;
    }
}
