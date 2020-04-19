package com.ivo.test.rest;

import com.ivo.rest.RestService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public class RestServiceTest extends AbstractTest {

    @Autowired
    private RestService restService;

    @Test
    public void test() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse("2019-02-02");
        Date toDate = sdf.parse("2020-04-10");
        String employee = "C1703015";
        List list = restService.getTravelDetailsFromBe(fromDate, toDate, employee);
        list.size();
    }
}
