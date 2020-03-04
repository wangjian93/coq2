package com.ivo.coq.project.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 机种每个阶段的EE信息
 * 数据来源PLM的机种阶段信息，以及EE单和ED单（ED -> EE -> 产品ID、工单）
 * @author wj
 * @version 1.0
 */
@Entity
public class ProjectEE extends AutoIncreaseEntityModel {

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
     * PLM中PLM制程，作判断厂别
     */
    private String process;

    /**
     * PLM中投产日期
     */
    private Date inDate;

    /**
     * PLM中产出日期
     */
    private Date outDate;

    /**
     * PLM中投入数量
     */
    private Double inQuantity;

    /**
     * PLM中产出数量
     */
    private Double outQuantity;

    /**
     * PLM中单位
     */
    private String units;

    /**
     * PLM中是否完成
     */
    private boolean isComplete;

    /**
     * PLM中单号（EE单或ED单）
     */
    private String  orderNumber;


    /*############################# 以上为PLM中数据，下面为从对应的EE单中获取的数据 ####################################*/

    /**
     * 注：
     * ARRAY使用的产品ID为productId
     * CELL使用的产品ID为PFCD (BEOL/TFT/CF)也是产品ID
     * MODULE的使用工单
     **/

    /**
     * EE单
     */
    private String eeOrder;

    /**
     * Product ID
     */
    private String productId;

    /**
     * PFCD(BEOL)
     */
    private String pfcd;

    /**
     * PFCD(TFT)
     */
    private String tft;

    /**
     * PFCD(CF)
     */
    private String cf;

    /**
     * 工单
     */
    private String wo;


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

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Double getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(Double inQuantity) {
        this.inQuantity = inQuantity;
    }

    public Double getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(Double outQuantity) {
        this.outQuantity = outQuantity;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEeOrder() {
        return eeOrder;
    }

    public void setEeOrder(String eeOrder) {
        this.eeOrder = eeOrder;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPfcd() {
        return pfcd;
    }

    public void setPfcd(String pfcd) {
        this.pfcd = pfcd;
    }

    public String getTft() {
        return tft;
    }

    public void setTft(String tft) {
        this.tft = tft;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getWo() {
        return wo;
    }

    public void setWo(String wo) {
        this.wo = wo;
    }
}
