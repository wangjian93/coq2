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
@Entity(name = "coq_report_In_Loss_Amount_detail")
public class InLossAmountDetail extends AutoIncreaseEntityModel {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FAB_DATE")
    private Date  fabDate;

    @Column(name = "FAB_ID")
    private String  fabId;

    private String PFCD;

    private String PROD_ID;

    private String MTRL_ID;

    private String PROD_SIZE_ID;

    private String PROD_MODEL_ID;

    private String SCRAP_SHT;

    private String SCRAP_PNL;

    private String CUT;

    private String VER_ID;

    private String FAB_SCRAP_TYPE;

    private Double PRICE;
}
