package com.ivo.coq.costCategory.directMaterial.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import com.ivo.coq.project.entity.EngineeringExperiment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 厂内直材部分费用
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_cost_Direct_material_detail")
@Setter
@Getter
public class MaterialCostDetail extends AutoIncreaseEntityModel {

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
     * ee单
     */
    private String ee;

    /**
     * 厂别
     */
    private String plant;

    /**
     * 费用
     * 1.LCM:工单费用
     * 2.Array:展BOM单片材料费用*实验数量
     * 3.CELL:工程试验单中料号单价*实验数量理论参考量
     */
    private Double amount;

    @OneToMany(mappedBy = "materialCostDetail", cascade = CascadeType.ALL)
    List<MaterialArrayProductAmount> productAmountList;

    @OneToMany(mappedBy = "materialCostDetail", cascade = CascadeType.ALL)
    List<MaterialCellMaterialAmount> materialAmountList;

    @OneToMany(mappedBy = "materialCostDetail", cascade = CascadeType.ALL)
    List<MaterialLcmWoAmount> woAmountList;

    public MaterialCostDetail() {}

    public MaterialCostDetail(EngineeringExperiment engineeringExperiment) {
        this.project = engineeringExperiment.getProject();
        this.stage = engineeringExperiment.getSample().getStage();
        this.time = engineeringExperiment.getSample().getTime();
        this.ee = engineeringExperiment.getEeOrder();
        this.plant = engineeringExperiment.getPlant();
    }
}
