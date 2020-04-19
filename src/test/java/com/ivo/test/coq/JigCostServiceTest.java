package com.ivo.test.coq;

import com.ivo.coq.costCategory.jig.service.JigCostDetailService;
import com.ivo.coq.costCategory.jig.service.JigCostService;
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
public class JigCostServiceTest extends AbstractTest {

    @Autowired
    private JigCostService jigCostService;

    @Autowired
    private JigCostDetailService jigCostDetailService;

    private static final String PROJECT = "N1568V R0";

    @Test
    public void a1_createJigCost() {
        jigCostService.createJigCost(PROJECT);
    }

    @Test
    public void a2_syncJigCostDetail() {
        jigCostDetailService.syncJigCostDetail(PROJECT);
    }

    @Test
    public void a3_computeJigCost() {
        jigCostService.computeJigCost(PROJECT);
    }
}
