package com.ivo.coq.report.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_report_In_Loss_Amount_detail_lcm")
public class InLossAmountDetailLcm extends AutoIncreaseEntityModel {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FAB_DATE")
    private Date fabDate;

    @Column(name = "ZYEAR")
    private String zYear;

    @Column(name = "ZMONTH")
    private String  zMonth;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "VERID")
    private String verId;

    @Column(name = "WO_ID")
    private String woId;

    @Column(name = "PLANT")
    private String plant;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "PROD_MODEL_ID")
    private String prodModelId;

    @Column(name = "PROD_SIZE_ID")
    private String prodSizeId;
}
