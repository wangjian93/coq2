package com.ivo.coq.costCategory.reworkScrap.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import javax.persistence.Entity;

/**
 * 重工报废费用详细
 * @author wj
 * @version 1.0
 */
@Entity
public class ReworkScrapCostDetail extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 次数
     */
    private Integer time;

    /**
     * 厂别
     */
    private String plant;

    /**
     * 站点
     */
    private String station;

    /**
     * 类型：重工 or 报废
     */
    private String reworkScrapType;

    /**
     * 数量
     */
    private Double quantity;

    /**
     * 单片进过站点的费用
     */
    private Double perAmount;

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

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getReworkScrapType() {
        return reworkScrapType;
    }

    public void setReworkScrapType(String reworkScrapType) {
        this.reworkScrapType = reworkScrapType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPerAmount() {
        return perAmount;
    }

    public void setPerAmount(Double perAmount) {
        this.perAmount = perAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
