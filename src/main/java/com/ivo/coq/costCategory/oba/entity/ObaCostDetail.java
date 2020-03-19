package com.ivo.coq.costCategory.oba.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;
import java.util.Date;

/**
 * OBA费用详情
 * @author wj
 * @version 1.0
 */
@Entity
public class ObaCostDetail extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

    /**
     * PE单号
     */
    private String peNumber;

    /**
     * 发生时间
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date happenTime;

    /**
     * OBA类型
     */
    private String obaType;

    /**
     * 数量
     */
    private Double quantity;

    /**
     * 价格
     */
    private Double price;

    /**
     * 费用
     */
    private Double amount;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPeNumber() {
        return peNumber;
    }

    public void setPeNumber(String peNumber) {
        this.peNumber = peNumber;
    }

    public Date getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Date happenTime) {
        this.happenTime = happenTime;
    }

    public String getObaType() {
        return obaType;
    }

    public void setObaType(String obaType) {
        this.obaType = obaType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
