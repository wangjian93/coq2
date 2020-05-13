package com.ivo.coq.report.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Array/cell/lcm的中制造成本，来自ESI的内部失败成本报表
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_report_total_Amount")
public class TotalAmount extends AutoIncreaseEntityModel {

    private String mon;

    private String plant;

    private Double amount;
}
