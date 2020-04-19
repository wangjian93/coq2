package com.ivo.coq.project.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机种
 * 数据来源PLM机种
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_project")
@Setter
@Getter
public class Project extends AutoIncreaseEntityModel {

    /**
     * 机种名
     */
    private String project;

    /**
     * 机种类型
     */
    private String type;

    /**
     * 机种尺寸
     */
    private String size;
}
