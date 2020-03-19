package com.ivo.coq.costCategory.travel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 差旅费用详情
 * @author wj
 * @version 1.0
 */
@Entity
public class TravelCostDetail extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 成本类型，属于预防、内损
     */
    private String costType;

    /**
     * 员工工号
     */
    private String employee;

    /**
     * 出差单号
     */
    private String travelNumber;

    /**
     * 出差日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date travelDate;

    /**
     * 交通费
     */
    private Double transportationAmount;

    /**
     * 旅馆费
     */
    private Double accommodationAmount;

    /**t
     * 电话费
     */
    private Double telphoneAmount;

    /**
     * 膳杂费
     */
    private Double miscAmount;

    /**
     * 补贴扣款
     */
    private Double ivomiscAmount;

    /**
     * 交际费
     */
    private Double entertainmentAmount;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getTravelNumber() {
        return travelNumber;
    }

    public void setTravelNumber(String travelNumber) {
        this.travelNumber = travelNumber;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Double getTransportationAmount() {
        return transportationAmount;
    }

    public void setTransportationAmount(Double transportationAmount) {
        this.transportationAmount = transportationAmount;
    }

    public Double getAccommodationAmount() {
        return accommodationAmount;
    }

    public void setAccommodationAmount(Double accommodationAmount) {
        this.accommodationAmount = accommodationAmount;
    }

    public Double getTelphoneAmount() {
        return telphoneAmount;
    }

    public void setTelphoneAmount(Double telphoneAmount) {
        this.telphoneAmount = telphoneAmount;
    }

    public Double getMiscAmount() {
        return miscAmount;
    }

    public void setMiscAmount(Double miscAmount) {
        this.miscAmount = miscAmount;
    }

    public Double getIvomiscAmount() {
        return ivomiscAmount;
    }

    public void setIvomiscAmount(Double ivomiscAmount) {
        this.ivomiscAmount = ivomiscAmount;
    }

    public Double getEntertainmentAmount() {
        return entertainmentAmount;
    }

    public void setEntertainmentAmount(Double entertainmentAmount) {
        this.entertainmentAmount = entertainmentAmount;
    }
}
