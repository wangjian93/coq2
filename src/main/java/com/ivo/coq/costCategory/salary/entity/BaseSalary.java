package com.ivo.coq.costCategory.salary.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 基本薪资
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_cost_salary_base")
@Setter
@Getter
public class BaseSalary extends AutoIncreaseEntityModel {

    public static final String LABEL_BASE = "基本薪资";
    public static final String LABEL_RD = "RD标准工时底薪";

    /**
     * 分类：基本薪资 / RD标准工时底薪
     */
    private String label;

    /**
     * 基本薪资
     */
    private Double baseSalary;
}
