package com.ivo.rest.bm.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 从BM获取的PR信息模型，用于外包薄化费用、治工具费用、验证费用
 * BM获取外包薄化费用，通过OEE得到的PR查询，费用为PO中的费用
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
public class BmModel {

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

}
