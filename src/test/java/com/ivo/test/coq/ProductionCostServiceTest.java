package com.ivo.test.coq;

import com.ivo.coq.costCategory.production.service.ProductionCostDetailService;
import com.ivo.coq.costCategory.production.service.ProductionCostService;
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
public class ProductionCostServiceTest extends AbstractTest {

    @Autowired
    private ProductionCostDetailService productionCostDetailService;

    @Autowired
    private ProductionCostService productionCostService;

    private static final String PROJECT = "N1568V R0";

    @Test
    public void a1_syncProductionCostDetail() {
        productionCostDetailService.syncProductionCostDetail(PROJECT);
    }

    @Test
    public void a2_computeProductionCostDetail() {
        productionCostDetailService.computeProductionCostDetail(PROJECT);
    }

    @Test
    public void a3_createProductionCost() {
        productionCostService.createProductionCost(PROJECT);
    }

    @Test
    public void a4_computeProductionCost() {
        productionCostService.computeProductionCost(PROJECT);
    }
}
