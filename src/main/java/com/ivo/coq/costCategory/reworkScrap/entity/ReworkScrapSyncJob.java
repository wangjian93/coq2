package com.ivo.coq.costCategory.reworkScrap.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import com.ivo.coq.project.entity.Sample;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Array/cell/lcm重工报废同步任务
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_rework_scrap_sync_job")
@Getter
@Setter
public class ReworkScrapSyncJob extends AutoIncreaseEntityModel {

    // 表任务完成状态
    public static final String STATUS_COMP = "COMP";
    // 表任务等待状态
    public static final String STATUS_WAIT = "WAIT";

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
    private Date inDate;

    /**
     * 产出日期
     */
    private Date outDate;

    /**
     * 任务完成状态
     */
    private String status;

    /**
     * 厂别
     */
    private String plant;

    /**
     * BEOL PFCD（PRODUCT）
     */
    private String product;

    /**
     * TFT PFCD
     */
    private String tft;

    /**
     * CF PFCD
     */
    private String cf;

    /**
     * 工单
     */
    private String wo;

    /**
     * 工单对应的Product
     */
    private String wo_product;

    public ReworkScrapSyncJob() {}
    public ReworkScrapSyncJob(Sample sample) {
        this.project = sample.getProject();
        this.stage = sample.getStage();
        this.time = sample.getTime();
        this.inDate = sample.getInDate();
        this.outDate = sample.getOutDate();
    }

}
