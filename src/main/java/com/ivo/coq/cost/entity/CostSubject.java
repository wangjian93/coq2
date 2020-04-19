package com.ivo.coq.cost.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机种成本详细（各阶段的成本二级科目）
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_cost_subject")
@Setter
@Getter
public class CostSubject extends AutoIncreaseEntityModel {

    /**
     * 机种名
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

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

    public CostSubject() {}

    public CostSubject(String project, String stage) {
        this.project = project;
        this.stage = stage;
    }
}
