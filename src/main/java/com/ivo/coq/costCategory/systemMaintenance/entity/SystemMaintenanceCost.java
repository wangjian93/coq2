package com.ivo.coq.costCategory.systemMaintenance.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;

/**
 * 系统维护折旧费
 * @author wj
 * @version 1.0
 */
@Entity
public class SystemMaintenanceCost extends AutoIncreaseEntityModel {

    /**
     * 机种名
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 阶段次数
     */
    private Integer time;

    /**
     * 总费用
     */
    private Double amount;

    /**
     * 鉴定部分
     */
    private Double identityAmount;

    /**
     * 内损部分
     */
    private Double inLossAmount;

    public SystemMaintenanceCost() {}

    public SystemMaintenanceCost(String project, String stage, Integer time) {
        this.project = project;
        this.stage = stage;
        this.time = time;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getIdentityAmount() {
        return identityAmount;
    }

    public void setIdentityAmount(Double identityAmount) {
        this.identityAmount = identityAmount;
    }

    public Double getInLossAmount() {
        return inLossAmount;
    }

    public void setInLossAmount(Double inLossAmount) {
        this.inLossAmount = inLossAmount;
    }
}
