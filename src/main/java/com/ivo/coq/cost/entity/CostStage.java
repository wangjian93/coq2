package com.ivo.coq.cost.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机种各阶段的成本详细
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_cost_stage")
@Setter
@Getter
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
}
