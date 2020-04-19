package com.ivo.coq.costCategory.directMaterial.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 直接材料成本 = 厂内直材费用 + 外包薄化部分费用 - 出货费用
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_cost_Direct_material")
@Setter
@Getter
public class DirectMaterialCost extends AutoIncreaseEntityModel {

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
     * 厂内直材费用
     */
    private Double directAmount;

    /**
     * 外包薄化部分费用
     */
    private Double outsourcingThinningAmount;

    /**
     * 出货费用
     */
    private Double shipmentAmount;

    /**
     * 总费用
     */
    private Double amount;


    public DirectMaterialCost() {}

    public DirectMaterialCost(String project, String stage, Integer time) {
        this.project = project;
        this.stage = stage;
        this.time = time;
    }
}
