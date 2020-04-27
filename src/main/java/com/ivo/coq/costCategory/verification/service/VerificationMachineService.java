package com.ivo.coq.costCategory.verification.service;

import com.ivo.coq.costCategory.verification.entity.VerificationMachine;

import java.util.List;

/**
 * 验证机台服务
 * @author wj
 * @version 1.0
 */
public interface VerificationMachineService {

    /**
     * 获取验证机台
     * @param machineName 机台名
     * @return VerificationMachine
     */
    VerificationMachine getVerificationMachine(String machineName);

    /**
     * 获取温湿度类机台
     * @return 温湿度类机台
     */
    List<VerificationMachine> getHumitureVerificationMachines();

    /**
     * 获取非温湿度机台
     * @return  非温湿度类机台
     */
    List<VerificationMachine> getNoHumitureVerificationMachines();
}
