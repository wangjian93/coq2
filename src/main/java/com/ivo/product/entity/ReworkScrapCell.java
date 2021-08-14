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
@Table(name = "Rework_Scrap_Cell")
public class ReworkScrapCell extends AutoIncreaseEntityModel {

    @Column(name = "FAB_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fabDate;
    private String PROD_ID;
    private String ARY_PROD_ID;
    private String PRODUCT_TYP;
    private String EVT_CATE;
    private double QTY;
    private String PROD_MODEL_ID;
    private String NX_OPE_ID;

    /**
     * 站点的单片重工/报废的费用
     */
    private double stationAmount;

    private double amount;
}
