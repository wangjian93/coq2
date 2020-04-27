package com.ivo.test.coq;

import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostDetailService;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostService;
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
public class ReworkScrapCostServiceTest extends AbstractTest {

    @Autowired
    private ReworkScrapCostService reworkScrapCostService;

    @Autowired
    private ReworkScrapCostDetailService detailService;

    private static final String PROJECT = "N1568V R0";

    @Test
    public void a1_computeReworkScrapCostDetail() {
        detailService.computeReworkScrapCostDetail(PROJECT);
    }

    @Test
    public void a2_createReworkScrapCost() {
        reworkScrapCostService.createReworkScrapCost(PROJECT);
    }

    @Test
    public void a3_computeReworkScrapCost() {
        reworkScrapCostService.computeReworkScrapCost(PROJECT);
    }
}
