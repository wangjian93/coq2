package com.ivo.product.repository;

import com.ivo.product.entity.Mard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface MardRepository extends JpaRepository<Mard, Long> {

    /**
     * 筛选厂别、日期区间
     * @param WERKS 厂别集合
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    List<Mard> findByWERKSInAndERSDABetween(List<String> WERKS, Date fromDate, Date toDate);

    List<Mard> findByERSDABetween(Date fromDate, Date toDate);
}
