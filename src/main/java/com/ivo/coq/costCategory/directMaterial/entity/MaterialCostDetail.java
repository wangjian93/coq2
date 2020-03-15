package com.ivo.coq.costCategory.directMaterial.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import com.ivo.coq.project.entity.EngineeringExperiment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 直接材料成本的材料部分费用详细
 * @author wj
 * @version 1.0
 */
@Entity
public class MaterialCostDetail extends AutoIncreaseEntityModel {

    /**
     * 机种
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
     * 工程实验单信息
     */
    @ManyToOne
    @JoinColumn(name = "EngineeringExperiment_FK")
    private EngineeringExperiment engineeringExperiment;

    /**
     * 单片材料费用 （ARRAY/CELL）
     */
    private Double perMaterialAmount;

    /**
     * 工单中费用 （LCM）
     */
    private Double woAmount;

    /**
     * 材料部分费用
     * ARRAY/CELL：单片材料费用 * 投产数量
     * LCM：工单中费用
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

    public EngineeringExperiment getEngineeringExperiment() {
        return engineeringExperiment;
    }

    public void setEngineeringExperiment(EngineeringExperiment engineeringExperiment) {
        this.engineeringExperiment = engineeringExperiment;
    }

    public Double getPerMaterialAmount() {
        return perMaterialAmount;
    }

    public void setPerMaterialAmount(Double perMaterialAmount) {
        this.perMaterialAmount = perMaterialAmount;
    }

    public Double getWoAmount() {
        return woAmount;
    }

    public void setWoAmount(Double woAmount) {
        this.woAmount = woAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
