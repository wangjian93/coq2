package com.ivo.rest.plm.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * PLM中机种的项目成员
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
public class PlmMember {
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
    private String user;

    /**
     * 员工姓名
     */
    private String name;
}
