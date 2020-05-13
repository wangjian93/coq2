package com.ivo.coq.report.repository;

import com.ivo.coq.report.entity.InLossAmount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface InLossAmountRepository extends JpaRepository<InLossAmount, Long> {

    List<InLossAmount> findByPlantAndFabDateBetween(String plant, Date fromDate, Date toDate);
}
