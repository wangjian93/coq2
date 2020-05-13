package com.ivo.rest.qms.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * QMS-新产品验证信息
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
public class QmsVerification {
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
    private String verificationQuantity;

    /**
     * QMS - 单位
     */
    private String verificationUnit;

    /**
     * QMS - 阶段
     */
    private String verificationStage;
}
