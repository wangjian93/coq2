package com.ivo.rest.oracle.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
public class OracleReworkScrapCell {

    private Date FAB_DATE;
    private String PROD_ID;
    private String ARY_PROD_ID;
    private String PRODUCT_TYP;
    private String NX_OPE_ID;
    private String EVT_CATE;
    private Double QTY;
    private Double QTY_0800;
    private String PROD_MODEL_ID;
}
