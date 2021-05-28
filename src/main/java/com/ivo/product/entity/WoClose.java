package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * LCM量产工单超耗数量
 * 来源：MDC-2546物料超耗率报表
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "Wo_Close")
@Setter
@Getter
public class WoClose extends AutoIncreaseEntityModel {

    /**
     * 工厂
     */
    private String WERKS;

    /**
     * 日期
     */
    @Column(name = "CLOSE_DATE")
    private Date closeDate;

    /**
     * 工单
     */
    private String AUFNR;

    /**
     * 工单类型
     */
    private String AUFNR_T;

    /**
     * 成品料号
     */
    private String MTRL_ID;

    /**
     * 机种
     */
    private String PROJECT;

    /**
     * 料号
     */
    private String MATNR;

    /**
     * 超耗数量
     */
    private double QTY;


    /**
     * 料号价格
     * 通过查询EXF.SZA_PSI_MBEW
     */
    private double price;
}
