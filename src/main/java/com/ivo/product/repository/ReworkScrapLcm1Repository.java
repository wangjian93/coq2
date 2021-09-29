package com.ivo.product.repository;

import com.ivo.product.entity.ReworkScrapLcm1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapLcm1Repository extends JpaRepository<ReworkScrapLcm1, Long> {

    List<ReworkScrapLcm1> findByFabDateBetween(Date fromDate, Date toDate);

    @Query(value = "from ReworkScrapLcm1 a where a.EVT_CATE='SCRP' and a.fabDate >=:fromDate and a.fabDate<=:toDate")
    List<ReworkScrapLcm1> getScrap(Date fromDate, Date toDate);

    /**
     * 统计量产品的重工报废费用
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    @Query(value = "select sum(a.amount) from ReworkScrapLcm1 a where substring(a.WO_ID, 3, 1) in ('1','8') " +
            " and fab_date>=:fromDate and fab_date<=:toDate")
    Double getMpReworkScrapAmount(Date fromDate, Date toDate);
}
