package com.ivo.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Rework_Scrap_Lcm1")
public class ReworkScrapLcm1 extends AutoIncreaseEntityModel {
    @Column(name = "FAB_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fabDate;
    private String WO_ID;
    private String CR_OPE_ID;
    private String EVT_CATE;
    private Double QTY;
    private String SCRP_M_OPE_ID;
    private String PROD_ID;
    private String PROD_MODEL_ID;

    /**
     * 站点的单片重工/报废的费用
     */
    private double stationAmount;

    private double amount;
}
