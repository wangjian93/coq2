package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * OBA费用
 * 来源：QMS-【产品售后服务费用管理报表】
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "oba")
public class Oba extends AutoIncreaseEntityModel {

    /**
     * 机种
     */
    private String project;

    /**
     * PE单号
     */
    private String peNumber;

    /**
     * 发生时间
     */
    private Date happenTime;

    /**
     * OBA类型
     */
    private String obaType;

    /**
     * 数量
     */
    private double quantity;

    /**
     * 价格
     */
    private double price;

    /**
     * 费用
     */
    private double amount;

    /**
     * 维护人
     */
    private String createUser;

    /**
     * 厂别  LCM CELL
     */
    private String fab;

    private String productType;
}
