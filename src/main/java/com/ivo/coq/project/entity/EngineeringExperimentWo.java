package com.ivo.coq.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * LCM工程实验单中的工单
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_project_Engineering_Experiment_Wo")
@Setter
@Getter
public class EngineeringExperimentWo extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * ee单
     */
    private String ee;

    /**
     * 工单
     */
    private String wo;

    @ManyToOne
    @JoinColumn(name = "engineeringExperiment_id")
    @JsonIgnore
    private EngineeringExperiment engineeringExperiment;

    public EngineeringExperimentWo() {}

    public EngineeringExperimentWo(EngineeringExperiment engineeringExperiment, String project, String ee) {
        this.engineeringExperiment = engineeringExperiment;
        this.project = project;
        this.ee = ee;
    }
}
