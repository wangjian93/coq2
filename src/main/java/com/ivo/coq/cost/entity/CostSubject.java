package com.ivo.coq.cost.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机种成本详细（各阶段的成本二级科目）
 * @author wj
 * @version 1.0
 */
@Entity
public class CostSubject extends AutoIncreaseEntityModel {

    /**
     * 机种名
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

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

    public CostSubject() {}

    public CostSubject(String project, String stage) {
        this.project = project;
        this.stage = stage;
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
}
