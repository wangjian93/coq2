package com.ivo.product.repository;

import com.ivo.product.entity.WoClose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface WoCloseRepository extends JpaRepository<WoClose, Long> {

    /**
     * 筛选厂别、日期
     * @param werks 厂别
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return  List<WoClose>
     */
    List<WoClose> findByWERKSAndCloseDateBetween(String werks, Date fromDate, Date toDate);

    List<WoClose> findByCloseDateBetween(Date fromDate, Date toDate);

}
