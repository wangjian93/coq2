package com.ivo.coq.costCategory.verification.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 验证费用
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_cost_verification")
@Setter
@Getter
public class VerificationCost extends AutoIncreaseEntityModel {

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
     * 总费用
     */
    private Double amount;

    /**
     * BM验证费用部分
     */
    private Double bmVerificationAmount;

    /**
     * 厂内验证费用部分
     */
    private Double inPlantVerificationAmount;

    public VerificationCost() {}

    public VerificationCost(String project, String stage, Integer time) {
        this.project = project;
        this.stage = stage;
        this.time = time;
    }
}
