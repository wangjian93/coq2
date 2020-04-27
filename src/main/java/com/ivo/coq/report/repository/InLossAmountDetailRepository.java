package com.ivo.coq.report.repository;

import com.ivo.coq.report.entity.InLossAmountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface InLossAmountDetailRepository extends JpaRepository<InLossAmountDetail, Long> {

    List<InLossAmountDetail> findByFabIdAndFabDate(String FAB_ID, Date FAB_DATE);
}
