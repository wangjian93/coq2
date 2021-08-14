package com.ivo;

import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
@RestController
public class TestController {

    @Resource
    private EntityManagerFactory emf;

    @GetMapping("/test")
    public Result test() {
        String sql = "SELECT\n" +
                "MONTH, FAB, \n" +
                "am as 内损,\n" +
                "(select amount from wh_scrap_month where `month`=a.`MONTH` and fab=a.FAB) as 仓库报废金额,\n" +
                "(select amount from rework_scrap_month where `month`=a.`MONTH` and fab=a.FAB) as 重工报废,\n" +
                "(select amount from wo_close_month where `month`=a.`MONTH` and fab=a.FAB) as 超耗费用,\n" +
                "(select amount from mard_month where `month`=a.`MONTH` and fab=a.FAB) as 入库费用,\n" +
                "(select amount from Total_Product_Cost where REPLACE(`month`, '-', '')=a.`MONTH` and fab=a.FAB) as 总制造费用\n" +
                "FROM \n" +
                "(\n" +
                "select  DATE_FORMAT(fab_date,'%Y%m') as month, plant as fab, amount as am from coq_report_in_loss_amount where fab_date>='2021-01-01' and fab_date<'2021-05-01'\n" +
                ")A\n" +
                "ORDER BY FAB,MONTH";

        EntityManager em = emf.createEntityManager();
        Query query = em.createNativeQuery(sql);
        List<Map> list = query.getResultList();
        em.close();
        return ResultUtil.success(list);
    }
}
