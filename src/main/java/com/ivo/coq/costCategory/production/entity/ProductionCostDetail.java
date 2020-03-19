package com.ivo.coq.costCategory.production.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;

/**
 * 生产费用明细
 * @author wj
 * @version 1.0
 */
@Entity
public class ProductionCostDetail extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 次数
     */
    private Integer time;

    /**
     * Process
     */
    private String Process;

    /**
     * 投产数量
     */
    private Double inQuantity;

    /**
     * 厂别
     */
    private String plant;

    /**
     * 单片费用
     */
    private Double perAmount;

    /**
     * 生产费用
     */
    private Double amount;


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

    public String getProcess() {
        return Process;
    }

    public void setProcess(String process) {
        Process = process;
    }

    public Double getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(Double inQuantity) {
        this.inQuantity = inQuantity;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public Double getPerAmount() {
        return perAmount;
    }

    public void setPerAmount(Double perAmount) {
        this.perAmount = perAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
