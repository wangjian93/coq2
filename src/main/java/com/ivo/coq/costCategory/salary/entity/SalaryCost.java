package com.ivo.coq.costCategory.salary.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 人员工资费用
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_cost_salary")
@Setter
@Getter
public class SalaryCost extends AutoIncreaseEntityModel {

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
     * 总费用
     */
    private Double amount;

    /**
     * 预防部分
     */
    private Double preventionAmount;

    /**
     * 内损部分
     */
    private Double inLossAmount;

    public SalaryCost() {}

    public SalaryCost(String project, String stage, Integer time) {
        this.project = project;
        this.stage = stage;
        this.time = time;
    }
}
