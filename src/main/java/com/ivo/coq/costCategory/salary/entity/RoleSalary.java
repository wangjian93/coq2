package com.ivo.coq.costCategory.salary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 岗位人员的平均本薪
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_role_salary")
@Setter
@Getter
public class RoleSalary extends AutoIncreaseEntityModel {

    private String type;

    private String role;

    private double salary;

    private String version;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    private boolean validFlag = true;
}
