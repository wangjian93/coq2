package com.ivo.product.repository;

import com.ivo.product.entity.ReworkScrapCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCellRepository extends JpaRepository<ReworkScrapCell, Long> {

    List<ReworkScrapCell> findByFabDateBetween(Date fromDate, Date toDate);


    /**
     * 统计量产品的重工报废费用
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    @Query(value = "select sum(a.amount) from ReworkScrapCell a where substring(a.PROD_ID, 1, 1) in ('P','R') " +
            " and fab_date>=:fromDate and fab_date<=:toDate and a.EVT_CATE in ('PIRW','RWTP','QTRW','REWK','REVW','RESS','FERW','ARWK','SCRP') ")
    Double getMpReworkScrapAmount(Date fromDate, Date toDate);
}
