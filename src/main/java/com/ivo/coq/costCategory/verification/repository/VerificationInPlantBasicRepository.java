package com.ivo.coq.costCategory.verification.repository;

import com.ivo.coq.costCategory.verification.entity.VerificationInPlantBasic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface VerificationInPlantBasicRepository extends JpaRepository<VerificationInPlantBasic, Long> {

    /**
     * 筛选年份
     * @param year
     * @return
     */
    List<VerificationInPlantBasic> findByYear(int year);

    /**
     * 筛选年份和基础数据名称
     * @param year
     * @param name
     * @return
     */
    VerificationInPlantBasic findFirstByYearAndName(int year, String name);
}
