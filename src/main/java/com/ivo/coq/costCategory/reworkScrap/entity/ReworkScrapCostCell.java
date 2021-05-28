package com.ivo.coq.costCategory.reworkScrap.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Array的重工报废数据
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_rework_scrap_Cell")
@Setter
@Getter
public class ReworkScrapCostCell extends AutoIncreaseEntityModel {
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
     * 投产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inDate;

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
    private String prodId;
    private String aryProdId;
    private String productType;
    private String nxOpeId;
    private String evtCate;
    private Double qty;
    private Double qty0800;
    private String ProdModelId;

    public ReworkScrapCostCell() {}
    public ReworkScrapCostCell(ReworkScrapSyncJob job) {
        this.project = job.getProject();
        this.stage = job.getStage();
        this.time = job.getTime();
        this.jobId = job.getId();
    }
}
