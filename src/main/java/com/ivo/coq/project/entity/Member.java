package com.ivo.coq.project.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机种的项目成员
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_project_member")
@Setter
@Getter
public class Member extends AutoIncreaseEntityModel {

    /**
     * 机种名
     */
    private String project;

    /**
     * 角色名
     */
    private String role;

    /**
     * 员工工号
     */
    private String employeeId;

    /**
     * 员工姓名
     */
    private String employeeName;
}
