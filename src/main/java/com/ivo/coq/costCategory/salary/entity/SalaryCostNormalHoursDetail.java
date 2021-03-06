package com.ivo.coq.costCategory.salary.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 人员工资标准工时部分
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_cost_salary_normal_hours_detail")
@Setter
@Getter
public class SalaryCostNormalHoursDetail extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 角色
     */
    private String role;

    /**
     * 标准工时
     */
    private Double workDays;

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

    public SalaryCostNormalHoursDetail() {}
    public SalaryCostNormalHoursDetail(String project) {
        this.project = project;
    }
}
