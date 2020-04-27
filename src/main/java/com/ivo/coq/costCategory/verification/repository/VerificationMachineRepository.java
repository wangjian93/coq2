package com.ivo.coq.costCategory.verification.repository;

import com.ivo.coq.costCategory.verification.entity.VerificationMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 实验机台仓库
 * @author wj
 * @version 1.0
 */
public interface VerificationMachineRepository extends JpaRepository<VerificationMachine, Long> {

    /**
     * 筛选机台名称
     * @param machineName 机台名
     * @return VerificationMachine
     */
    VerificationMachine findFirstByMachineName(String machineName);

    /**
     * 筛选温湿度类机台和非温湿度类机台
     * @param humitureType 温湿度类型
     * @return List
     */
    List<VerificationMachine> findByHumitureType(String humitureType);
}
