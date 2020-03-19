package com.ivo.coq.costCategory.verification.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;

/**
 * @author wj
 * @version 1.0
 */
@Entity
public class VerificationCostPlantDetail extends AutoIncreaseEntityModel {

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
     * 验证类型
     */
    private String verificationType;

    /**
     * 验证项目
     */
    private String verificationSubject;

    /**
     * 实验条件
     */
    private String verificationCondition;

    /**
     * 数量
     */
    private Double verificationQuantity;

    /**
     * 单位
     */
    private String verificationUnit;


    /**
     * 单片人力费用
     */
    private Double manPowerAmountPer;

    /**
     * 单片维护费用
     */
    private Double maintainAmountPer;

    /**
     * 单片电费
     */
    private Double electricityBillAmountPer;

    /**
     * 人力费用
     */
    private Double manPowerAmount;

    /**
     * 维护费用
     */
    private Double maintainAmount;

    /**
     * 电费
     */
    private Double electricityBillAmount;

    /**
     * 产内验证费用：人力费用 + 维护费用 + 电费
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

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }

    public String getVerificationSubject() {
        return verificationSubject;
    }

    public void setVerificationSubject(String verificationSubject) {
        this.verificationSubject = verificationSubject;
    }

    public String getVerificationCondition() {
        return verificationCondition;
    }

    public void setVerificationCondition(String verificationCondition) {
        this.verificationCondition = verificationCondition;
    }

    public Double getVerificationQuantity() {
        return verificationQuantity;
    }

    public void setVerificationQuantity(Double verificationQuantity) {
        this.verificationQuantity = verificationQuantity;
    }

    public String getVerificationUnit() {
        return verificationUnit;
    }

    public void setVerificationUnit(String verificationUnit) {
        this.verificationUnit = verificationUnit;
    }

    public Double getManPowerAmountPer() {
        return manPowerAmountPer;
    }

    public void setManPowerAmountPer(Double manPowerAmountPer) {
        this.manPowerAmountPer = manPowerAmountPer;
    }

    public Double getMaintainAmountPer() {
        return maintainAmountPer;
    }

    public void setMaintainAmountPer(Double maintainAmountPer) {
        this.maintainAmountPer = maintainAmountPer;
    }

    public Double getElectricityBillAmountPer() {
        return electricityBillAmountPer;
    }

    public void setElectricityBillAmountPer(Double electricityBillAmountPer) {
        this.electricityBillAmountPer = electricityBillAmountPer;
    }

    public Double getManPowerAmount() {
        return manPowerAmount;
    }

    public void setManPowerAmount(Double manPowerAmount) {
        this.manPowerAmount = manPowerAmount;
    }

    public Double getMaintainAmount() {
        return maintainAmount;
    }

    public void setMaintainAmount(Double maintainAmount) {
        this.maintainAmount = maintainAmount;
    }

    public Double getElectricityBillAmount() {
        return electricityBillAmount;
    }

    public void setElectricityBillAmount(Double electricityBillAmount) {
        this.electricityBillAmount = electricityBillAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
