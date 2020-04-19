package com.ivo.coq.costCategory.verification.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 验证费用-厂内验证费用
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_cost_verification_plant_detail")
@Setter
@Getter
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
     * QMS - 验证类型
     */
    private String verificationType;

    /**
     * QMS - 验证项目
     */
    private String verificationSubject;

    /**
     * QMS - 实验条件
     */
    private String verificationCondition;

    /**
     * QMS - 数量
     */
    private Double verificationQuantity;

    /**
     * QMS - 单位
     */
    private String verificationUnit;

    /**
     * QMS - 阶段
     */
    private String verificationStage;

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
}
