package com.ivo.np.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 新产品开发失败成本率
 * Rate = 每月机种（IA+IT+MD）总的Total多余的花费 / 每月机种（IA+IT+MD）总的花费 * 100%
 * 机种需属于新品（MP阶段未Closed）
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Np_Failure_Cost_Rate")
public class FailureCostRate extends AutoIncreaseEntityModel {

    /**
     * 月份 (例如 '202101')
     */
    String month;

    /**
     * 预防费用
     */
    private double totalPreventionCost;

    /**
     * 鉴定费用
     */
    private double totalIdentityCost;

    /**
     * 内损费用
     */
    private double totalInLossCost;

    /**
     * 外损费用
     */
    private double totalOutLossCost;

    /**
     * 必要费用
     */
    private double totalNecessaryCost;

    /**
     * 多余费用
     */
    private double totalRedundantCost;

    /**
     * 开发失败成本率
     */
    private double rate;
}
