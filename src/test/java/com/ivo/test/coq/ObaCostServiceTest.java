package com.ivo.test.coq;

import com.ivo.coq.costCategory.oba.service.ObaCostDetailService;
import com.ivo.coq.costCategory.oba.service.ObaCostService;
import com.ivo.test.AbstractTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ObaCostServiceTest extends AbstractTest {

    @Autowired
    private ObaCostService obaCostService;

    @Autowired
    private ObaCostDetailService obaCostDetailService;

    private static final String PROJECT = "N1568V R0";

    /**
     * 创建机种的OBA费用
     */
    @Test
    public void a1_createObaCost() {
        obaCostService.createObaCost(PROJECT);
    }

    /**
     * 从QMS同步机种的OBA明细
     */
    @Test
    public void a2_syncObaCostDetail() {
        obaCostDetailService.syncObaCostDetail(PROJECT);
    }

    /**
     * 计算机种的OBA费用
     */
    @Test
    public void a3_computeObaCost() {
        obaCostService.computeObaCost(PROJECT);
    }
}
