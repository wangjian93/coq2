package com.ivo.coq.costCategory.verification.service;

import com.ivo.coq.costCategory.verification.entity.VerificationSubject;

import java.util.List;

/**
 * 验证项目服务
 * @author wj
 * @version 1.0
 */
public interface VerificationSubjectService {

    /**
     * 获取验证项目
     * @param subjectName 验证项目名称
     * @return VerificationSubject
     */
    VerificationSubject getVerificationSubject(String subjectName);

    /**
     * 获取验证项目列表
     * @return List
     */
    List<VerificationSubject> getVerificationSubjects();

    /**
     * 获取温湿度类验证项目
     * @return List
     */
    List<VerificationSubject> getHumitureVerificationSubjects();

    /**
     * 获取非温湿度类验证项目
     * @return List
     */
    List<VerificationSubject> getNoHumitureVerificationSubjects();


    /**
     * 计算验证项目的单片电费
     */
    void computeElectricityBillPer();
}
