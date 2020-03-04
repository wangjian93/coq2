package com.ivo.coq.project.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 机种的进度，各阶段的时间
 * 数据来源PLM的项目进度
 * @author wj
 * @version 1.0
 */
@Entity
public class ProjectSchedule extends AutoIncreaseEntityModel {

    /**
     * 机种名
     */
    private String project;

    /**
     * 版本
     */
    private String version;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 时间
     */
    private Date date;

    /**
     * 状态
     */
    private String status;

    /**
     * 顺序
     */
    private int sort;


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
