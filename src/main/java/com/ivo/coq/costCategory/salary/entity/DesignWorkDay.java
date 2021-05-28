package com.ivo.coq.costCategory.salary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 研发标准时间
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_cost_salary_role_work_days")
@Setter
@Getter
public class DesignWorkDay extends AutoIncreaseEntityModel {

    private String type;

    /**
     * 角色
     */
    private String role;

    /**
     * 工作天数
     */
    private double workDays;

    private String version;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    private boolean validFlag = true;
}
