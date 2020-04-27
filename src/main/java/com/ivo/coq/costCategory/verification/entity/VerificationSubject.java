package com.ivo.coq.costCategory.verification.entity;

import com.ivo.common.model.EntityModel;
import lombok.*;

import javax.persistence.*;

/**
 * 验证项目
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_Verification_Subject")
public class VerificationSubject extends EntityModel {

    private static final long serialVersionUID = 4676132693483316877L;

    public static final String Humiture_Type_Yes = "Humiture";

    public static final String Humiture_Type_No = "NoHumiture";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 验证项目名称
     */
    @NonNull
    private String subjectName;

    /**
     * 温湿度类型：温湿度类和非温湿度类
     */
    @NonNull
    private String humitureType;

    /**
     * 机台名称
     */
    @NonNull
    private String machineName;

    /**
     * 实验时间 （小时）
     */
    private Double verificationTime;

    /**
     * 单位
     */
    private String unit;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 验证类型
     */
    private String verificationType;

    /**
     * 实验条件
     */
    private String verificationCondition;

    /**
     * 单片电费
     */
    private Double electricityBillPer;
}
