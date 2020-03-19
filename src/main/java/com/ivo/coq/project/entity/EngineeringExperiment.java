package com.ivo.coq.project.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * ARRAY/CELL/LCM工程实验信息，来源工程实验单
 * ARRAY: ProductID
 * CELL: PFCD/TFT/CF
 * LCM: 工单
 * @author wj
 * @version 1.0
 */
@Entity
public class EngineeringExperiment extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 实验信息
     */
    @ManyToOne
    @JoinColumn(name = "Sample_FK")
    private Sample sample;

    /**
     * EE单
     */
    private String eeOrder;

    /**
     * 工厂：ARRAY/CELL/LCM
     */
    private String plant;

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

    public EngineeringExperiment() {}

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public String getEeOrder() {
        return eeOrder;
    }

    public void setEeOrder(String eeOrder) {
        this.eeOrder = eeOrder;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
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
