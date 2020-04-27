package com.ivo.coq.costCategory.reworkScrap.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 重工报废费用
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_cost_rework_scrap")
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
}
