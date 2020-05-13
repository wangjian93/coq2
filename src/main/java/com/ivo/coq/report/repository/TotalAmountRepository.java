package com.ivo.coq.report.repository;

import com.ivo.coq.report.entity.TotalAmount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface TotalAmountRepository extends JpaRepository<TotalAmount, Long> {

    TotalAmount findFirstByPlantAndMon(String plant, String mon);
}
