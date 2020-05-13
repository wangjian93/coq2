package com.ivo.coq.report.repository;

import com.ivo.coq.report.entity.InLossAmountDetailArrayCell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface InLossAmountDetailArrayCellRepository extends JpaRepository<InLossAmountDetailArrayCell, Long> {
    List<InLossAmountDetailArrayCell> findByFabIdAndFabDate(String fabId, Date fabDate);
}
