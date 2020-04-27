package com.ivo.coq.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 单片机种经过的站点分摊站点成本，由IE提供
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_station_cost_share")
public class StationCostShare extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * 厂别
     */
    private String factory;

    /**
     * 站点
     */
    private String station;

    /**
     * 站点描述
     */
    private String description;

    /**
     * 机台
     */
    private String machine;


    // 1. Total分摊站点成本（生产、报废）部分
    /**
     * 人工
     */
    private Double manpower;

    /**
     * 间耗材
     */
    private Double ndirectMaterial;

    /**
     * 修理
     */
    private Double  repair;

    /**
     * 能耗
     */
    private Double energy;

    /**
     * 折旧
     */
    private Double depreciation;

    /**
     * 其他
     */
    private Double other;

    /**
     * 生产/报废的总费用
     */
    private Double total;


    // 2. 分摊站点成本（重工）部分
    /**
     * 人工
     */
    private Double manpower_;

    /**
     * 间耗材
     */
    private Double ndirectMaterial_;

    /**
     * 修理
     */
    private Double  repair_;

    /**
     * 能耗
     */
    private Double energy_;

    /**
     * 折旧
     */
    private Double depreciation_;

    /**
     * 其他
     */
    private Double other_;

    /**
     * 重工的总费用
     */
    private Double total_;

    /**
     * 站点先后经过的顺序
     */
    private int sort;
}
