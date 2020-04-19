package com.ivo.rest.eifdb.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 工程实验单中的产品
 * Array: PRODUCT
 * CELL: BEOL PFCD（PRODUCT）、TFT PFCD、CF PFCD
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
public class EifEngineeringExperimentProduct {

    /**
     * BEOL PFCD（PRODUCT）
     */
    private String product;

    /**
     * TFT PFCD
     */
    private String tft;

    /**
     * CF PFCD
     */
    private String cf;

    /**
     * 实验数量
     */
    private Double expQty;
}
