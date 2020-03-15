package com.ivo.coq.costCategory.reworkScrap.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;

import javax.persistence.Entity;

/**
 * 重工报废费用
 * @author wj
 * @version 1.0
 */
@Entity
public class ReworkScrapCost extends AutoIncreaseEntityModel {

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
     * 总费用
     */
    private Double amount;

    /**
     * 重工费用
     */
    private Double reworkAmount;

    /**
     * 报废费用
     */
    private Double scrapAmount;

    public ReworkScrapCost() {}

    public ReworkScrapCost(String project, String stage, Integer time) {
        this.project = project;
        this.stage = stage;
        this.time = time;
    }

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getReworkAmount() {
        return reworkAmount;
    }

    public void setReworkAmount(Double reworkAmount) {
        this.reworkAmount = reworkAmount;
    }

    public Double getScrapAmount() {
        return scrapAmount;
    }

    public void setScrapAmount(Double scrapAmount) {
        this.scrapAmount = scrapAmount;
    }
}
