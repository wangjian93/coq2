package com.ivo.coq.costCategory.salary.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 角色的工作日天数
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_cost_salary_role_work_days")
@Setter
@Getter
public class RoleWorkDays extends AutoIncreaseEntityModel {

    /**
     * 角色
     */
    private String role;

    /**
     * 工作天数
     */
    private Double workDays;
}
