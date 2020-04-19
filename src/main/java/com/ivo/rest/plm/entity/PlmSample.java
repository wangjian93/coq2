package com.ivo.rest.plm.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * PLM机种的实验信息
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
public class PlmSample {
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
     * PROCESSTIME
     */
    private String processTime;

    /**
     * PLM中投产日期
     */
    private Date inDate;

    /**
     * PLM中产出日期
     */
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
}
