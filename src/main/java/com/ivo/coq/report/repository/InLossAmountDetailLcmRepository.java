package com.ivo.coq.report.repository;

import com.ivo.coq.report.entity.InLossAmountDetailLcm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface InLossAmountDetailLcmRepository extends JpaRepository<InLossAmountDetailLcm, Long> {

    List<InLossAmountDetailLcm> findByPlantAndFabDate(String plant, Date fabDate);
}
