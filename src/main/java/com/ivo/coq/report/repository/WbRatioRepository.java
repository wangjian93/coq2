package com.ivo.coq.report.repository;

import com.ivo.coq.report.entity.WbRatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface WbRatioRepository extends JpaRepository<WbRatio, Long> {

    List<WbRatio> findByFabIdAndMonInAndRatioNotNullOrderByMonAsc(String fabId, List<String> months);

    @Query(value = "select *,\n" +
            "(price+obaAmount)/amount as ratio from (\n" +
            "select \n" +
            "* ,\n" +
            "(SELECT case when amount is null then 0 else amount end amount FROM oba_month WHERE month=t.`MONTH` AND fab=t.fab LIMIT 1) as obaAmount\n" +
            "from (\n" +
            "select\n" +
            "CASE WHEN fab_id='WB_CEL' THEN 'CELL' ELSE 'LCM' END fab,\n" +
            "price,\n" +
            "amount,\n" +
            "REPLACE (`mon`, '-', '') as month\n" +
            "from coq_report_wb_Ratio\n" +
            "where REPLACE (`mon`, '-', '')>=:fromMonth and REPLACE (`mon`, '-', '')<=:toMonth \n" +
            ") t\n" +
            ")tt where fab = :fab" +
            " order by month asc ", nativeQuery = true)
    List<Map> getWebFailureRate(String fromMonth, String toMonth, String fab);

    @Query(value = "select replace(mon, '-', '') as month, amount, fab_id from coq_report_wb_Ratio \n" +
            "where fab_id=:fabId AND replace(mon, '-', '') in :monthList ", nativeQuery = true)
    List<Map> getTotalProductAmount(String fabId, List<String> monthList);
}
