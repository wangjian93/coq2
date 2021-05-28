package com.ivo.product.repository;

import com.ivo.product.entity.ReworkScrapMonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapMonthRepository extends JpaRepository<ReworkScrapMonth, Long> {

    /**
     * 筛选月份
     * @param month 月份
     * @return
     */
    List<ReworkScrapMonth> findByMonth(String month);
}
