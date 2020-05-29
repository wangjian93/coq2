package com.ivo.coq.report.repository;

import com.ivo.coq.report.entity.WbRatio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface WbRatioRepository extends JpaRepository<WbRatio, Long> {

    List<WbRatio> findByFabIdAndMonInAndRatioNotNullOrderByMonAsc(String fabId, List<String> months);
}
