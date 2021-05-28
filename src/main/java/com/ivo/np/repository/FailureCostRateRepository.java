package com.ivo.np.repository;

import com.ivo.np.entity.FailureCostRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface FailureCostRateRepository extends JpaRepository<FailureCostRate, Long> {

    /**
     * 筛选条件月份
     * @param fromMonth 开发月份
     * @param toMonth 结束月份
     * @return List<FailureCostRate>
     */
    List<FailureCostRate> findByMonthGreaterThanEqualAndMonthLessThanEqual(String fromMonth, String toMonth);

}
