package com.ivo.coq.costCategory.verification.service;

import com.ivo.coq.costCategory.verification.entity.VerificationInPlantBasic;

import java.util.List;

/**
 * 厂内验证基础数据服务
 * @author wj
 * @version 1.0
 */
public interface VerificationInPlantBasicService {

    /**
     * 获取指定年份的基础数据
     * @param year 年份
     * @return List
     */
    List<VerificationInPlantBasic> getVerificationInPlantBasics(int year);

    /**
     * 获取实验室DL人力
     * @param year 年份
     * @return Double
     */
    Double getManPower(int year);

    /**
     * 获取实验室DL人员月薪基数
     * @param year 年份
     * @return Double
     */
    Double getMonthlySalary(int year);

    /**
     * 获取温湿度实验机台总能耗
     * @param year 年份
     * @return Double
     */
    Double getHumitureTotalPower(int year);

    /**
     * 获取电费价格
     * @param year 年份
     * @return Double
     */
    Double getElectricityBillPrice(int year);

    /**
     * 获取全年实验数量
     * @param year 年份
     * @return Double
     */
    Double getVerificationQuantity(int year);

    /**
     * 获取全年温湿度类实验数量
     * @param year 年份
     * @return Double
     */
    Double getHumitureVerificationQuantity(int year);

    /**
     * 获取全年ORT验证
     * @param year 年份
     * @return Double
     */
    Double getOrtVerificationQuantity(int year);

    /**
     * 获取全年工程材料变更验证
     * @param year 年份
     * @return Double
     */
    Double getMaterialChangeVerificationQuantity(int year);

    /**
     * 获取全年其他验证
     * @param year 年份
     * @return Double
     */
    Double getOtherVerificationQuantity(int year);

    /**
     * 获取全年新产品验证
     * @param year 年份
     * @return Double
     */
    Double getNewProductVerificationQuantity(int year);

    /**
     * 获取全年机台维护费用
     * @param year 年份
     * @return Double
     */
    Double getMaintainCost(int year);

    /**
     * 获取单片人力费用
     * @param year
     * @return 年份
     */
    Double getManPowerCostPer(int year);

    /**
     * 获取单片维护费用
     * @param year 年份
     * @return Double
     */
    Double getMaintainCostPer(int year);

    /**
     * 计算温湿度机台总能耗
     * @param year 年份
     */
    void computeHumitureTotalPower(int year);

    /**
     * 计算单片人力费用
     * 计算方式： DL人力数量*月薪*12个月/全年实验数量
     * @param year 年份
     */
    void computeManPowerCostPer(int year);

    /**
     * 计算单片维护费用
     * 计算方式：全年机台维护费用/全年实验数量
     * @param year 年份
     */
    void computeMaintainCostPer(int year);
}
