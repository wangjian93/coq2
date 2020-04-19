package com.ivo.rest.eifdb.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 面板工程实验单-CELL中料号
 * @author wj
 * @version 1.0
 */
@Getter
@Setter
public class EifEngineeringExperimentMaterial {

    /**
     * 物料组
     */
    private String materialGroup;

    /**
     * 料号
     */
    private String material;

    /**
     * 实验数量理论参考量
     */
    private Double expQty;

    private String project;

    private String ee;
}
