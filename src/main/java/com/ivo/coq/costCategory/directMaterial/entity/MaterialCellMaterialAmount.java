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
 * Cell厂直材费用
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_cost_Direct_Material_detail_cell")
@Setter
@Getter
public class MaterialCellMaterialAmount extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * ee单
     */
    private String ee;

    /**
     * 料号
     */
    private String material;

    /**
     * 料号单价
     */
    private Double materialPrice;

    /**
     * 实验数量理论参考量
     */
    private Double quantity;

    /**
     * 料号单价 *  实验数量理论参考量
     */
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "materialCostDetail_FK")
    @JsonIgnore
    private MaterialCostDetail materialCostDetail;

    public MaterialCellMaterialAmount() {}

    public MaterialCellMaterialAmount(String project, String ee, MaterialCostDetail materialCostDetail) {
        this.project = project;
        this.ee = ee;
        this.materialCostDetail = materialCostDetail;
    }
}
