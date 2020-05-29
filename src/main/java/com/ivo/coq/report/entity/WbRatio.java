package com.ivo.coq.report.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 外部失败成本率
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_report_wb_Ratio")
public class WbRatio extends AutoIncreaseEntityModel {

    private String fabId;

    private Double price;

    private Double amount;

    private String mon;

    private Double ratio;
}
