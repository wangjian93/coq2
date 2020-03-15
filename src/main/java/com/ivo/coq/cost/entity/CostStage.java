package com.ivo.coq.cost.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;

/**
 * 机种各阶段的成本详细
 * @author wj
 * @version 1.0
 */
@Entity
public class CostStage extends AutoIncreaseEntityModel {

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
     * 直接材料费用
     */
    private Double directMaterialCost;

    /**
     * 治工具费用
     */
    private Double jigCost;

    /**
     * 验证费用
     */
    private Double verificationCost;

    /**
     * 生产费用
     */
    private Double productionCost;

    /**
     * 重工报废费用
     */
    private Double reworkScrapCost;

    /**
     * 人员工资费用
     */
    private Double salaryCost;

    /**
     * 差旅交际费用
     */
    private Double travelCost;

    /**
     * RMA费用
     */
    private Double rmaCost;

    /**
     * OBA费用
     */
    private Double obaCost;

    /**
     * 赔偿费用
     */
    private Double compensationCost;

    /**
     * 系统维护折旧费用
     */
    private Double systemMaintenanceCost;


    public CostStage() {}

    public CostStage(String project, String stage, Integer time) {
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

    public Double getDirectMaterialCost() {
        return directMaterialCost;
    }

    public void setDirectMaterialCost(Double directMaterialCost) {
        this.directMaterialCost = directMaterialCost;
    }

    public Double getJigCost() {
        return jigCost;
    }

    public void setJigCost(Double jigCost) {
        this.jigCost = jigCost;
    }

    public Double getVerificationCost() {
        return verificationCost;
    }

    public void setVerificationCost(Double verificationCost) {
        this.verificationCost = verificationCost;
    }

    public Double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(Double productionCost) {
        this.productionCost = productionCost;
    }

    public Double getReworkScrapCost() {
        return reworkScrapCost;
    }

    public void setReworkScrapCost(Double reworkScrapCost) {
        this.reworkScrapCost = reworkScrapCost;
    }

    public Double getSalaryCost() {
        return salaryCost;
    }

    public void setSalaryCost(Double salaryCost) {
        this.salaryCost = salaryCost;
    }

    public Double getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(Double travelCost) {
        this.travelCost = travelCost;
    }

    public Double getRmaCost() {
        return rmaCost;
    }

    public void setRmaCost(Double rmaCost) {
        this.rmaCost = rmaCost;
    }

    public Double getObaCost() {
        return obaCost;
    }

    public void setObaCost(Double obaCost) {
        this.obaCost = obaCost;
    }

    public Double getCompensationCost() {
        return compensationCost;
    }

    public void setCompensationCost(Double compensationCost) {
        this.compensationCost = compensationCost;
    }

    public Double getSystemMaintenanceCost() {
        return systemMaintenanceCost;
    }

    public void setSystemMaintenanceCost(Double systemMaintenanceCost) {
        this.systemMaintenanceCost = systemMaintenanceCost;
    }
}
