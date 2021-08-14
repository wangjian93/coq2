package com.ivo.product.repository;

import com.ivo.product.entity.WMM_SCRAP_DETAIL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ScrapDetailRepository extends JpaRepository<WMM_SCRAP_DETAIL, Long> {

    List<WMM_SCRAP_DETAIL> findByFabAndFabDateBetween(String fab, Date fromDate, Date toDate);
}
