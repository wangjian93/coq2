package com.ivo.coq.costCategory.directMaterial.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 外包薄化部分费用
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_cost_Direct_Material_Outsourcing_Thinning_detail")
@Setter
@Getter
public class OutsourcingThinningCostDetail extends AutoIncreaseEntityModel {

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
     * 外包制程工程试验单
     */
    private String oee;

    /**
     * PR采购请求单
     */
    private String pr;

    /**
     * PR起草人
     */
    private String prDrafter;

    /**
     * PR起草时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date prDrafterDate;

    /**
     * PR的Item
     */
    private String prItem;

    /**
     * 预算形式
     */
    private String budgetType;

    /**
     * 物料组
     */
    private String materialGroup;

    /**
     * 物料组描述
     */
    private String materialGroupName;

    /**
     * 预算编号
     */
    private String budgetNumber;

    /**
     * 品名规格
     */
    private String specification;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 数量
     */
    private Double quantity;

    /**
     * 币别
     */
    private String currency;

    /**
     * PR总费用
     */
    private Double prAmount;

    /**
     * PO总费用
     */
    private Double poAmount;

    public OutsourcingThinningCostDetail() {}

    public OutsourcingThinningCostDetail(String project, String stage, Integer time, String oee) {
        this.project = project;
        this.stage = stage;
        this.time = time;
        this.oee = oee;
    }
}
