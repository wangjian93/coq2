package com.ivo.coq.cost.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;

/**
 * 机种成本
 * @author wj
 * @version 1.0
 */
@Entity
public class Cost extends AutoIncreaseEntityModel {

    /**
     * 机种名
     */
    private String project;

    /**
     * 预防费用
     */
    private Double preventionCost;

    /**
     * 鉴定费用
     */
    private Double identityCost;

    /**
     * 内损费用
     */
    private Double inLossCost;

    /**
     * 外损费用
     */
    private Double outLossCost;

    /**
     * 必要费用
     */
    private Double necessaryCost;

    /**
     * 多余费用
     */
    private Double redundantCost;

    public Cost() {}

    public Cost(String project) {
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Double getPreventionCost() {
        return preventionCost;
    }

    public void setPreventionCost(Double preventionCost) {
        this.preventionCost = preventionCost;
    }

    public Double getIdentityCost() {
        return identityCost;
    }

    public void setIdentityCost(Double identityCost) {
        this.identityCost = identityCost;
    }

    public Double getInLossCost() {
        return inLossCost;
    }

    public void setInLossCost(Double inLossCost) {
        this.inLossCost = inLossCost;
    }

    public Double getOutLossCost() {
        return outLossCost;
    }

    public void setOutLossCost(Double outLossCost) {
        this.outLossCost = outLossCost;
    }

    public Double getNecessaryCost() {
        return necessaryCost;
    }

    public void setNecessaryCost(Double necessaryCost) {
        this.necessaryCost = necessaryCost;
    }

    public Double getRedundantCost() {
        return redundantCost;
    }

    public void setRedundantCost(Double redundantCost) {
        this.redundantCost = redundantCost;
    }
}
