package com.ivo.test.rest;

import com.ivo.coq.report.service.WbRatioService;
import com.ivo.rest.RestService;
import com.ivo.rest.eifdb.EifService;
import com.ivo.rest.oracle.OracleService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public class RestServiceTest extends AbstractTest {

    @Autowired
    private RestService restService;

    @Autowired
    private WbRatioService wbRatioService;

    @Autowired
    private OracleService oracleService;

    @Autowired
    private EifService eifService;

    @Test
    public void test() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse("2019-02-02");
        Date toDate = sdf.parse("2020-04-10");
        String employee = "C1703015";
        List list = restService.getTravelDetailsFromBe(fromDate, toDate, employee);
        list.size();
    }

    @Test
    public void test2() {
        System.out.println(oracleService.getWoAmount("IL1W212001"));
    }
}
