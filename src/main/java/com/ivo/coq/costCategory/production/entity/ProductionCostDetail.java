package com.ivo.coq.costCategory.production.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 生产费用明细
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_cost_production_detail")
public class ProductionCostDetail extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 次数
     */
    private Integer time;

    /**
     * Process
     */
    private String Process;

    /**
     * 投产数量
     */
    private Double inQuantity;

    /**
     * 试验单
     */
    private String orderNumber;

    /**
     * 厂别
     */
    private String plant;

    /**
     * 单片费用
     */
    private Double perAmount;

    /**
     * 生产费用
     */
    private Double amount;

    public ProductionCostDetail() {}
    public ProductionCostDetail(String project, String stage, Integer time) {
        this.project = project;
        this.stage = stage;
        this.time = time;
    }
}
