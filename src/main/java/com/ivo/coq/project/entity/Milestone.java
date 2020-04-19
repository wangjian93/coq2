package com.ivo.coq.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 机种的进度
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_project_milestone")
@Setter
@Getter
public class Milestone extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 阶段
     */
    private String milestone;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 原计划的结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDatePlan;
}
