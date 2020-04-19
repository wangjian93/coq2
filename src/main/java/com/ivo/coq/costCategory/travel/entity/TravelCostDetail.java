package com.ivo.coq.costCategory.travel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 差旅费用详情
 * @author wj
 * @version 1.0
 */
@Entity
@Setter
@Getter
public class TravelCostDetail extends AutoIncreaseEntityModel {

    public static final String TYPE_preventionCost = "预防";
    public static final String TYPE_inLossCost = "内损";

    /**
     * 机种
     */
    private String project;

    /**
     * 成本类型，属于预防、内损
     * KICK OFF ~ NPRB 属于预防
     * NPRB ~ DESIGN 属于内损
     */
    private String costType;

    /**
     * 员工工号
     */
    private String employee;

    /**
     * 出差单号
     */
    private String travelNumber;

    /**
     * 出差日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date travelDate;

    /**
     * 交通费
     */
    private Double transportationAmount;

    /**
     * 旅馆费
     */
    private Double accommodationAmount;

    /**t
     * 电话费
     */
    private Double telphoneAmount;

    /**
     * 膳杂费
     */
    private Double miscAmount;

    /**
     * 补贴扣款
     */
    private Double ivomiscAmount;

    /**
     * 交际费
     */
    private Double entertainmentAmount;

    public TravelCostDetail() {}
    public TravelCostDetail(String project, String costType) {
        this.project = project;
        this.costType = costType;
    }
}
