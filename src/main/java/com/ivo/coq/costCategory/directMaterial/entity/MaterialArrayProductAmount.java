package com.ivo.coq.costCategory.directMaterial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_cost_Direct_Material_detail_array")
@Setter
@Getter
public class MaterialArrayProductAmount extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * ee单
     */
    private String ee;

    /**
     * 产品
     */
    private String product;

    /**
     * 单片费用（BOM展开得到单片材料费用）
     */
    private Double perAmount;

    /**
     * 实验数量
     */
    private Double quantity;

    /**
     * 单片费用 * 实验数量
     */
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "materialCostDetail_FK")
    @JsonIgnore
    private MaterialCostDetail materialCostDetail;

    public MaterialArrayProductAmount() {}

    public MaterialArrayProductAmount(String project, String ee, MaterialCostDetail materialCostDetail) {
        this.project = project;
        this.ee = ee;
        this.materialCostDetail = materialCostDetail;
    }
}
