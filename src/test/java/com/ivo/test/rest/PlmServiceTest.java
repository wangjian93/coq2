package com.ivo.test.rest;

import com.ivo.rest.plm.PlmService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public class PlmServiceTest extends AbstractTest {

    @Autowired
    private PlmService plmService;

    private static final String PROJECT = "N1568V R0";

    @Test
    public void test1() {
        List list = plmService.getProjectSample(PROJECT);
        Assert.notEmpty(list, "");
    }

    @Test
    public void test2() {
        List list = plmService.getMember(PROJECT);
        Assert.notEmpty(list, "");
    }

    @Test
    public void test3() {
        List list = plmService.getMilestone(PROJECT);
        Assert.notEmpty(list, "");
    }

}
