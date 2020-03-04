package com.ivo.coq.project.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;

/**
 * 机种
 * 数据来源PLM机种
 * @author wj
 * @version 1.0
 */
@Entity
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


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
