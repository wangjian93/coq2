package com.ivo.coq.costCategory.verification.entity;

import com.ivo.common.model.EntityModel;
import lombok.*;

import javax.persistence.*;

/**
 * 厂内验证的一些基础数据
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_Verification_In_Plant_Basic")
public class VerificationInPlantBasic extends EntityModel {

    private static final long serialVersionUID = -309181476751303256L;

    // 基础数据常量
    // 实验室DL人力（IVO+IVE）
    public static final String MAN_POWER = "manPower";
    // 实验室DL人员月薪基数
    public static final String MONTHLY_SALARY = "monthlySalary";
    // 温湿度实验机台总能耗
    public static final String HUMITURE_TOTAL_POWER = "humitureTotalPower";
    // 电费价格
    public static final String ELECTRICITY_BILL_PRICE = "electricityBillPrice";
    // 全年实验数量
    public static final String VERIFICATION_QUANTITY = "verificationQuantity";
    // 全年温湿度类实验数量
    public static final String HUMITURE_VERIFICATION_QUANTITY = "humitureVerificationQuantity";
    // 全年ORT验证
    public static final String ORT_VERIFICATION_QUANTITY = "ortVerificationQuantity";
    // 全年工程材料变更验证
    public static final String MATERIAL_CHANGE_VERIFICATION_QUANTITY = "materialChangeVerificationQuantity";
    // 全年其他验证
    public static final String OTHER_VERIFICATION_QUANTITY = "otherVerificationQuantity";
    // 全年新产品验证
    public static final String NEW_PRODUCT_VERIFICATION_QUANTITY = "newProductVerificationQuantity";
    // 全年机台维护费用
    public static final String MAINTAIN_COST = "maintainCost";

    // 单片人力费用
    public static final String MAN_POWE_Cost_Per = "manPowerCostPer";

    // 单片维护费用
    public static final String MAINTAIN_COST_Per = "maintainCostPer";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 年度
     */
    @NonNull
    private Integer year;

    /**
     * 数据名称
     */
    @NonNull
    private String name;

    /**
     * 数据标签
     */
    private String label;

    /**
     * 值
     */
    private Double value;

    /**
     * 单位
     */
    private String unit;
}
