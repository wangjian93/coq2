package com.ivo.coq.costCategory.salary.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_role_work_day")
@Setter
@Getter
public class RoleWorkDay extends AutoIncreaseEntityModel {

    /**
     * 角色
     */
    private String role;

    /**
     * 工作天数
     */
    private double workDays;
}
