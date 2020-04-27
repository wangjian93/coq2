package com.ivo.coq.costCategory.reworkScrap.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * LCM的重工报废数据
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_rework_scrap_lcm")
@Setter
@Getter
public class ReworkScrapCostLcm extends AutoIncreaseEntityModel {
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
     * 执行的任务ID
     */
    private Long jobId;

    /**
     * 匹配的站点
     */
    private String station;

    /**
     * 站点的单片费用
     */
    private Double stationAmount;

    /**
     * 重工/报废费用
     */
    private Double amount;


    private Date fabDate;
    private String woId;
    private String crOpeId;
    private String evtCate;
    private Double qty;
    private String scrpMOpeId;
    private String prodId;
    private String prodModelId;

    public ReworkScrapCostLcm() {}
    public ReworkScrapCostLcm(ReworkScrapSyncJob job) {
        this.project = job.getProject();
        this.stage = job.getStage();
        this.time = job.getTime();
        this.jobId = job.getId();
    }
}
