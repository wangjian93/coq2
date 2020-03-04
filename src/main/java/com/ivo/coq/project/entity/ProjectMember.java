package com.ivo.coq.project.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;

/**
 * 机种的项目成员
 * @author wj
 * @version 1.0
 */
@Entity
public class ProjectMember extends AutoIncreaseEntityModel {

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


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
