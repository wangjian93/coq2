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
@Table(name = "coq_cost_Direct_Material_detail_lcm")
@Setter
@Getter
public class MaterialLcmWoAmount extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * ee单
     */
    private String ee;

    /**
     * 工单
     */
    private String wo;

    /**
     * 工单费用
     */
    private Double woAmount;

    @ManyToOne
    @JoinColumn(name = "materialCostDetail_FK")
    @JsonIgnore
    private MaterialCostDetail materialCostDetail;

    public MaterialLcmWoAmount() {}

    public MaterialLcmWoAmount(String project, String ee, MaterialCostDetail materialCostDetail) {
        this.project = project;
        this.ee = ee;
        this.materialCostDetail = materialCostDetail;
    }
}
