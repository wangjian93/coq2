package com.ivo.coq.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * ARRAY/CELL/LCM工程实验信息，来源工程实验单
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_project_Engineering_Experiment")
@Setter
@Getter
public class EngineeringExperiment extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * EE单
     */
    private String eeOrder;

    /**
     * 工厂：ARRAY/CELL/LCM
     */
    private String plant;

    /**
     * 实验信息
     */
    @ManyToOne
    @JoinColumn(name = "Sample_ID")
    private Sample sample;

    /**
     * Array、cell工程试验单的产品
     */
    @OneToMany(mappedBy = "engineeringExperiment", cascade = CascadeType.ALL)
    List<EngineeringExperimentProduct> productList;

    /**
     * CELL工程试验单的料号
     */
    @OneToMany(mappedBy = "engineeringExperiment", cascade = CascadeType.ALL)
    List<EngineeringExperimentMaterial> materialList;

    /**
     * LCM工程试验单的工单
     */
    @OneToMany(mappedBy = "engineeringExperiment", cascade = CascadeType.ALL)
    List<EngineeringExperimentWo> woList;


    public EngineeringExperiment() {}

    public EngineeringExperiment(Sample sample, String eeOrder, String project) {
        this.sample = sample;
        this.eeOrder = eeOrder.trim().toUpperCase();
        this.project = project.trim().toUpperCase();
    }
}
