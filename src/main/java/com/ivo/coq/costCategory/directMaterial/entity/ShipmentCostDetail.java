package com.ivo.coq.costCategory.directMaterial.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 出货费用
 * @author wj
 * @version 1.0
 */
@Entity
@Table
@Setter
@Getter
public class ShipmentCostDetail extends AutoIncreaseEntityModel {

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
     * 出货数量：来源PLM
     */
    private Double quantity;

    /**
     * 出货产品单价：LCM工单金额/工单数量
     */
    private Double perAmount;

    /**
     * 费用
     */
    private Double amount;

    public ShipmentCostDetail() {}

    public ShipmentCostDetail(String project, String stage, Integer time) {
        this.project = project;
        this.stage = stage;
        this.time = time;
    }
}
