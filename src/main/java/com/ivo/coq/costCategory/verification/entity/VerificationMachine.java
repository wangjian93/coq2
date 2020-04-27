package com.ivo.coq.costCategory.verification.entity;
import com.ivo.common.model.EntityModel;
import lombok.*;
import javax.persistence.*;

/**
 * 实验机台：分为温湿度类实验机台和非温湿度类实验机台
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_Verification_Machine")
public class VerificationMachine extends EntityModel {

    private static final long serialVersionUID = -9063485932597072949L;

    public static final String Humiture_Type_Yes = "Humiture";

    public static final String Humiture_Type_No = "NoHumiture";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 验证机台名称
     */
    @NonNull
    private String machineName;

    /**
     * 温湿度类型：温湿度类和非温湿度类
     */
    @NonNull
    private String humitureType;

    /**
     * 功率 （单位W）
     */
    @NonNull
    private Double power;

    /**
     * 功率
     */
    private String powerStr;

    /**
     * 机台编号
     */
    private String machineNumber;

    /**
     * 机台厂牌
     */
    private String machineBrand;

    /**
     * 机台型号
     */
    private String machineModel;

    /**
     * 机台供应商
     */
    private String machineSupplier;

}
