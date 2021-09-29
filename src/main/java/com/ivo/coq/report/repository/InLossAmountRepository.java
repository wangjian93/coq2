package com.ivo.coq.report.repository;

import com.ivo.coq.report.entity.InLossAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface InLossAmountRepository extends JpaRepository<InLossAmount, Long> {

    List<InLossAmount> findByPlantAndFabDateBetween(String plant, Date fromDate, Date toDate);



    @Query(value = "SELECT\n" +
            "  MONTH, FAB,\n" +
            "  CASE WHEN 内损费用 IS NULL THEN 0 ELSE 内损费用 END 内损费用,\n" +
            "  CASE WHEN 报废损失外包费用 IS NULL THEN 0 ELSE 报废损失外包费用 END 报废损失外包费用,\n" +
            "  CASE WHEN 重工报废费用 IS NULL THEN 0 ELSE 重工报废费用 END 重工报废费用,\n" +
            "  CASE WHEN 超耗费用 IS NULL THEN 0 ELSE 超耗费用 END 超耗费用,\n" +
            "  CASE WHEN 入库费用 IS NULL THEN 0 ELSE 入库费用 END 入库费用,\n" +
            "  CASE WHEN 总制造费用 IS NULL THEN 0 ELSE 总制造费用 END 总制造费用\n" +
            "FROM (\n" +
            "  SELECT MONTH, FAB,\n" +
            "\t\tamount as 总制造费用,\n" +
            "\t\t(SELECT amount FROM wh_scrap_month WHERE `month` = a.`MONTH` AND fab = a.FAB LIMIT 1) AS 报废损失外包费用,\n" +
            "\t\t(SELECT amount FROM rework_scrap_month WHERE `month` = a.`MONTH` AND fab = a.FAB LIMIT 1) AS 重工报废费用,\n" +
            "\t\t(SELECT amount FROM wo_close_month WHERE `month` = a.`MONTH` AND fab = a.FAB LIMIT 1) AS 超耗费用,\n" +
            "\t\t(SELECT amount FROM mard_month WHERE `month` = a.`MONTH` AND fab = a.FAB LIMIT 1) AS 入库费用,\n" +
            "\t\tprice AS 内损费用\n" +
            "  FROM(\n" +
            "\t\tSELECT REPLACE (`month`, '-', '') AS MONTH,fab,amount,price FROM Total_Product_Cost \n" +
            "\t\tWHERE\n" +
            "\t\t\tREPLACE (`month`, '-', '') >= :fromMonth \n" +
            "\t\t\tAND REPLACE (`month`, '-', '') <= :toMonth \n" +
            "\t\t\tAND fab in :fabs \n" +
            "  ) A ORDER BY FAB,MONTH\n" +
            ") t", nativeQuery = true)
    List<Map> getRate(String fromMonth, String toMonth, String[] fabs);


    @Query(value = "SELECT * from (\n" +
            "\tselect \n" +
            "\t\tsum(identity_cost) as identity_cost, sum(in_loss_cost) as in_loss_cost,\n" +
            "\t\tsum(out_loss_cost) as out_loss_cost, sum(prevention_cost) as prevention_cost,\n" +
            "\t\tmonth \n" +
            "\tfrom (\n" +
            "\t\tselect * from (\n" +
            "\t\t\tSELECT\n" +
            "\t\t\t\tcase when in_loss_cost is null or in_loss_cost <0 then 0 else in_loss_cost end in_loss_cost,\n" +
            "\t\t\t\tcase when out_loss_cost is null or out_loss_cost <0 then 0 else out_loss_cost end out_loss_cost,\n" +
            "\t\t\t\tcase when identity_cost is null or identity_cost <0 then 0 else identity_cost end identity_cost,\n" +
            "\t\t\t\tcase when prevention_cost is null or prevention_cost <0 then 0 else prevention_cost end prevention_cost,\n" +
            "\t\t\t\t(select date_format(end_date, '%Y%m') from coq_project_milestone m where m.project=c.project and m.milestone like CONCAT(c.stage,'%')) as month\n" +
            "\t\t\tFROM coq_cost_subject c where project not in (\n" +
            "\t\t\t\t\t\t\t\t\tselect project  from coq_project_milestone where milestone='NPRB' and \n" +
            "\t\t\t\t\t\t\t\t\t\tend_date<='2020-01-01'\n" +
            "\t\t\t\t\t\t\t) \n" +
            "\t\t) t where month is not null\n" +
            "\t) tt GROUP BY month\n" +
            ") ttt\n" +
            "where month>=:fromMonth and month<=:toMonth", nativeQuery = true)
    List<Map> getNpFailureCostRate(String fromMonth, String toMonth);


    @Query(value = "select * from (\n" +
            "\tSELECT * ,\n" +
            "\t\t(select DISTINCT type from coq_project p where p.project=ttt.project LIMIT 0,1) as type,\n" +
            "\t\tcase when (in_loss_cost+out_loss_cost+identity_cost+prevention_cost) > 0 \n" +
            "       then (in_loss_cost+out_loss_cost)/(in_loss_cost+out_loss_cost+identity_cost+prevention_cost)*100\n" +
            "       else 0 \n" +
            "\t\tend as rate\n" +
            "  from (\n" +
            "                        select \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tproject,\n" +
            "                        sum(identity_cost) as identity_cost, \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tsum(in_loss_cost) as in_loss_cost,\n" +
            "                        sum(out_loss_cost) as out_loss_cost, \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tsum(prevention_cost) as prevention_cost\n" +
            "                        from (\n" +
            "                        select * from (\n" +
            "                        SELECT\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tproject,\n" +
            "                        case when in_loss_cost is null or in_loss_cost <0 then 0 else in_loss_cost end in_loss_cost,\n" +
            "                        case when out_loss_cost is null or out_loss_cost <0 then 0 else out_loss_cost end out_loss_cost,\n" +
            "                        case when identity_cost is null or identity_cost <0 then 0 else identity_cost end identity_cost,\n" +
            "                        case when prevention_cost is null or prevention_cost <0 then 0 else prevention_cost end prevention_cost,\n" +
            "                        (select date_format(end_date, '%Y%m') from coq_project_milestone m where m.project=c.project and m.milestone like CONCAT(c.stage,'%')) as month\n" +
            "                        FROM coq_cost_subject c\n" +
            "                        ) t where month in :monthList " +
            " and project not in (select project  from coq_project_milestone where milestone='NPRB' and end_date<='2020-01-01') \n" +
            "\t\t) tt GROUP BY project\n" +
            "\t) ttt\n" +
            ") tttt ORDER BY rate desc", nativeQuery = true)
    List<Map> getNpFailureCostRateDetail(List<String> monthList);

}
