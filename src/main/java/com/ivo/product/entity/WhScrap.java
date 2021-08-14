package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 仓库报废金额
 * 来源仓库报废单
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "Wh_Scrap")
@Setter
@Getter
public class WhScrap extends AutoIncreaseEntityModel {


    /**
     * 仓库报废单号
     */
    private String trackingNumber;

    /**
     * 报废金额
     */
    private double scrapMoneyCny;

    /**
     * 申请时间
     */
    private Date dateOfOrder;

    /**
     * 申请部门
     */
    private String userDepartment;

    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 机种
     */
    private String project;

    /**
     * 实验类别
     */
    private String expType;

    /**
     * 报废原因
     */
    private String costType;
}
