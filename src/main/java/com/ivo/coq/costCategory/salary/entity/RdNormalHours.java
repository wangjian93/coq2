package com.ivo.coq.costCategory.salary.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * RD的标准工时
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_cost_salary_rd_normal_hours")
@Setter
@Getter
public class RdNormalHours extends AutoIncreaseEntityModel {

    /**
     * 角色
     */
    private String role;

    /**
     * 工作天数
     */
    private Double workDays;
}
