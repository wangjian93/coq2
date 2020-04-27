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
public class OracleReworkScrapLcm {

    private Date FAB_DATE;
    private String WO_ID;
    private String CR_OPE_ID;
    private String EVT_CATE;
    private Double QTY;
    private String SCRP_M_OPE_ID;
    private String PROD_ID;
    private String PROD_MODEL_ID;
}
