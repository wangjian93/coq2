package com.ivo.station.repository;

import com.ivo.station.entity.WorkRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface WorkRateRepository extends JpaRepository<WorkRate, Long> {

    /**
     * 筛选成本中心
     * @param costCenter 成本中心
     * @return
     */
    List<WorkRate> findByCostCenter(String costCenter);

    /**
     * 获取成本中心的总费率
     * @param costCenter 成本中心
     * @return
     */
    @Query(value = "select sum(price) from Work_Rate where cost_center=:costCenter", nativeQuery=true)
    double getTotalPrice(String costCenter);

    /**
     * 获取人工费率
     * @param costCenter 成本中心
     * @return
     */
    @Query(value = "select sum(price) from work_rate  where cost_center=:costCenter and work_name='人工作业'", nativeQuery=true)
    double getM_price(String costCenter);

    /**
     * 获取其他费率
     * @param costCenter 成本中心
     * @return
     */
    @Query(value = "select sum(price) from work_rate  where cost_center=:costCenter and work_name<>'人工作业'", nativeQuery=true)
    double getOther_price(String costCenter);
}
