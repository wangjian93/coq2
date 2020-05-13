package com.ivo.coq.report.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Array/CELL/LCM内损费用，来自ESI的内部失败成本报表
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_report_In_Loss_Amount")
public class InLossAmount extends AutoIncreaseEntityModel {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date  fabDate;

    private String plant;

    private Double amount;
}
