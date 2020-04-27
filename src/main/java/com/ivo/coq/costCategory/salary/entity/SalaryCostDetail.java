package com.ivo.coq.costCategory.salary.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 人员薪资详细
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_cost_salary_detail")
@Setter
@Getter
public class SalaryCostDetail extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 角色
     */
    private String role;

    /**
     * 工作天数
     */
    private Double workDays;

    /**
     * 人数
     */
    private int personNumber;

    /**
     * Delay的天数
     */
    private Double delayDays;

    /**
     * 预防费用
     */
    private Double preventionAmount;

    /**
     * 内损费用
     */
    private Double inLossAmount;

    public SalaryCostDetail() {}

    public SalaryCostDetail(String project) {
        this.project = project;
    }
}
