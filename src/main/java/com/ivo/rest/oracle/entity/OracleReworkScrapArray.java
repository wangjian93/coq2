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
public class OracleReworkScrapArray {

    private Date FAB_DATE;
    private String PROD_ID;
    private String ROUTE_ID;
    private String EVT_CATE;
    private Double qty;
    private String PROD_MODEL_ID;
    private String OPE_ID;

}
