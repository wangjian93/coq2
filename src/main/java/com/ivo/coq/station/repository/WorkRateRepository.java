package com.ivo.coq.station.repository;

import com.ivo.coq.station.entity.WorkRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface WorkRateRepository {

    List<WorkRate> findByMonthAndCostCenter(String month, String costCenter);
}
