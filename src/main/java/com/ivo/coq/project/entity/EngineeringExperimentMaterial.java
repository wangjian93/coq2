package com.ivo.coq.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * CELL工程实验单中的料号
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_project_Engineering_Experiment_Material")
@Getter
@Setter
public class EngineeringExperimentMaterial extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * EE单
     */
    private String ee;

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

    @ManyToOne
    @JoinColumn(name = "engineeringExperiment_id")
    @JsonIgnore
    private EngineeringExperiment engineeringExperiment;

    public EngineeringExperimentMaterial() {}

    public EngineeringExperimentMaterial(EngineeringExperiment engineeringExperiment, String project, String ee) {
        this.engineeringExperiment = engineeringExperiment;
        this.project = project;
        this.ee = ee;
    }
}
