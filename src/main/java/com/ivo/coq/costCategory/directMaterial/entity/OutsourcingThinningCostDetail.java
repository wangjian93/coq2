package com.ivo.coq.costCategory.directMaterial.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 直接材料成本的外包薄化部分费用详细
 * @author wj
 * @version 1.0
 */
@Entity
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
    private Date peDrafterDate;

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


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getOee() {
        return oee;
    }

    public void setOee(String oee) {
        this.oee = oee;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    public String getPrDrafter() {
        return prDrafter;
    }

    public void setPrDrafter(String prDrafter) {
        this.prDrafter = prDrafter;
    }

    public Date getPeDrafterDate() {
        return peDrafterDate;
    }

    public void setPeDrafterDate(Date peDrafterDate) {
        this.peDrafterDate = peDrafterDate;
    }

    public String getPrItem() {
        return prItem;
    }

    public void setPrItem(String prItem) {
        this.prItem = prItem;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getMaterialGroup() {
        return materialGroup;
    }

    public void setMaterialGroup(String materialGroup) {
        this.materialGroup = materialGroup;
    }

    public String getMaterialGroupName() {
        return materialGroupName;
    }

    public void setMaterialGroupName(String materialGroupName) {
        this.materialGroupName = materialGroupName;
    }

    public String getBudgetNumber() {
        return budgetNumber;
    }

    public void setBudgetNumber(String budgetNumber) {
        this.budgetNumber = budgetNumber;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPrAmount() {
        return prAmount;
    }

    public void setPrAmount(Double prAmount) {
        this.prAmount = prAmount;
    }

    public Double getPoAmount() {
        return poAmount;
    }

    public void setPoAmount(Double poAmount) {
        this.poAmount = poAmount;
    }
}
