package com.ivo.coq.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 机种的实验管理信息，来源于PLM实验管理
 * @author wj
 * @version 1.0
 */
@Entity
public class Sample extends AutoIncreaseEntityModel {

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
     * Process
     */
    private String process;

    /**
     * PLM中投产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inDate;

    /**
     * PLM中产出日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
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
     * 实验申请单
     */
    private String  orderNumber;

    public Sample() {}

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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


}
