package com.ivo.product.repository;

import com.ivo.product.entity.ReworkScrapLcm2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapLcm2Repository extends JpaRepository<ReworkScrapLcm2, Long> {

    List<ReworkScrapLcm2> findByFabDateBetween(Date fromDate, Date toDate);


    /**
     * 统计量产品的重工报废费用(工单第三码为 1 8)
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    @Query(value = "select sum(a.amount) from ReworkScrapLcm2 a where substring(a.WO_ID, 3, 1) in ('1','8') " +
            " and fab_date>=:fromDate and fab_date<=:toDate")
    Double getMpReworkScrapAmount(Date fromDate, Date toDate);

}
