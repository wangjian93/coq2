package com.ivo.coq.costCategory.directMaterial.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;

/**
 * 直接材料费用
 * @author wj
 * @version 1.0
 */
@Entity
public class DirectMaterialCost extends AutoIncreaseEntityModel {

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
     * 直接材料部分费用
     */
    private Double directAmount;

    /**
     * 外包薄化部分费用
     */
    private Double outsourcingThinningAmount;

    /**
     * 总费用
     */
    private Double amount;


    public DirectMaterialCost() {}

    public DirectMaterialCost(String project, String stage, Integer time) {
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

    public Double getDirectAmount() {
        return directAmount;
    }

    public void setDirectAmount(Double directAmount) {
        this.directAmount = directAmount;
    }

    public Double getOutsourcingThinningAmount() {
        return outsourcingThinningAmount;
    }

    public void setOutsourcingThinningAmount(Double outsourcingThinningAmount) {
        this.outsourcingThinningAmount = outsourcingThinningAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
