package com.ivo.product.repository;

import com.ivo.product.entity.ReworkScrapAry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapAryRepository extends JpaRepository<ReworkScrapAry, Long> {

    List<ReworkScrapAry> findByFabDateBetween(Date fromDate, Date toDate);


    /**
     * 统计量产品的重工报废费用
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    @Query(value = "select sum(a.amount) from ReworkScrapAry a where substring(a.PROD_ID, 1, 2) in ('AP','AG','AV','AB','AD','OP','OB','FD','A2','A4','A6') " +
            " and a.OPE_ID like '%4' and fab_date>=:fromDate and fab_date<=:toDate")
    Double getMpReworkScrapAmount(Date fromDate, Date toDate);


    @Query(value = "from ReworkScrapAry a where substring(a.PROD_ID, 1, 2) in ('AP','AG','AV','AB','AD','OP','OB','FD','A2','A4','A6') " +
            " and a.OPE_ID like '%4' and fab_date>=:fromDate and fab_date<=:toDate")
    List<ReworkScrapAry> getMpReworkScrap(Date fromDate, Date toDate);
}
