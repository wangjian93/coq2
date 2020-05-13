package com.ivo.coq.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 机种的实验管理信息，来源于PLM实验管理
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_project_sample")
@Setter
@Getter
public class Sample extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 阶段次数
     */
    private Integer time;

    /**
     * Process
     */
    private String process;

    /**
     * processTime
     */
    private String processTime;

    /**
     * PLM中投产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inDate;

    /**
     * PLM中产出日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date outDate;

    /**
     * PLM中投入数量
     */
    private Double inQuantity;

    /**
     * PLM中产出数量
     */
    private Double outQuantity;

    /**
     * 实验申请单
     */
    private String  orderNumber;

    @OneToMany(mappedBy = "sample", cascade = CascadeType.ALL)
    @JsonIgnore
    List<EngineeringExperiment> engineeringExperimentList;

    /**
     * 出货数量
     */
    private Double shipment;
}
