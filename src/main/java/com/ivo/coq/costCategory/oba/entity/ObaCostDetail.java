package com.ivo.coq.costCategory.oba.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
/**
 * OBA费用详情
 * @author wj
 * @version 1.0
 */
@Entity
@Setter
@Getter
public class ObaCostDetail extends AutoIncreaseEntityModel {

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
     * PE单号
     */
    private String peNumber;

    /**
     * 发生时间
     */
    private String happenTime;

    /**
     * OBA类型
     */
    private String obaType;

    /**
     * 数量
     */
    private Double quantity;

    /**
     * 价格
     */
    private Double price;

    /**
     * 费用
     */
    private Double amount;

    public ObaCostDetail() {}

    public ObaCostDetail(String project) {
        this.project = project;
    }
}
