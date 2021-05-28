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
     * 获取基础数据
     * @return List
     */
    List<VerificationInPlantBasic> getVerificationInPlantBasics();

    /**
     * 获取基础数据
     * @param name 名称
     * @return Double
     */
    Double getBasicData(String name);

    /**
     * 计算温湿度机台总能耗
     */
    void computeHumitureTotalPower();

    /**
     * 计算单片人力费用
     * 计算方式： DL人力数量*月薪*12个月/全年实验数量
     */
    void computeManPowerCostPer();

    /**
     * 计算单片维护费用
     * 计算方式：全年机台维护费用/全年实验数量
     */
    void computeMaintainCostPer();

    /**
     * 计算单片温湿度机台能耗费用
     * 计算方式：温湿度类机台总能耗*24小时*365天/1000*电费/全年温湿度类实验数量
     */
    void computeHumitureCostPer();


    void save(List<VerificationInPlantBasic> list);
}
