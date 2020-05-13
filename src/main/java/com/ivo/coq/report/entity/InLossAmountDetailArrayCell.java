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
@Entity(name = "coq_report_In_Loss_Amount_detail_array_cell")
public class InLossAmountDetailArrayCell extends AutoIncreaseEntityModel {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FAB_DATE")
    private Date fabDate;

    @Column(name = "FAB_ID")
    private String fabId;

    @Column(name = "PFCD")
    private String pfcd;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "MTRL_ID")
    private String mtrlId;

    @Column(name = "PROD_SIZE_ID")
    private String prodSizeId;

    @Column(name = "PROD_MODEL_ID")
    private String prodModelId;

    @Column(name = "SCRAP_SHT")
    private String scrapSht;

    @Column(name = "SCRAP_PNL")
    private String scrapPnl;

    @Column(name = "CUT")
    private String cut;

    @Column(name = "VER_ID")
    private String verId;

    @Column(name = "FAB_SCRAP_TYPE")
    private String fabScrapType;

    @Column(name = "PRICE")
    private Double price;
}
