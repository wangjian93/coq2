package com.ivo.rest.plm.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * PLM中机种的进度
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
public class PlmMilestone {

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
    private Date endDate;

    /**
     * 原计划的结束日期
     */
    private Date endDatePlan;
}
