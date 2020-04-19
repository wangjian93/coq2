package com.ivo.coq.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Array/CELL工程试验单中的产品
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_project_Engineering_Experiment_Product")
@Getter
@Setter
public class EngineeringExperimentProduct extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * ee单
     */
    private String ee;

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

    @ManyToOne
    @JoinColumn(name = "engineeringExperiment_id")
    @JsonIgnore
    private EngineeringExperiment engineeringExperiment;

    public EngineeringExperimentProduct() {}

    public EngineeringExperimentProduct(EngineeringExperiment engineeringExperiment, String project, String ee) {
        this.engineeringExperiment = engineeringExperiment;
        this.ee = ee.trim().toUpperCase();
        this.project = project;
    }
}
