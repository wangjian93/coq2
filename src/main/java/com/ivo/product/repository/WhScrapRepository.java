package com.ivo.product.repository;

import com.ivo.product.entity.WhScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface WhScrapRepository extends JpaRepository<WhScrap, Long> {

    /**
     * 筛选厂别、日期区间
     * @param fab 厂别
     * @param fromDate 开始日期
     * @param toDae 结束日期
     * @return
     */
    List<WhScrap> findByFabAndDateOfOrderBetween(String fab, Date fromDate, Date toDae);
}
