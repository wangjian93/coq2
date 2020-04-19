package com.ivo.test.coq.cost;

import com.ivo.coq.costCategory.directMaterial.service.DirectMaterialCostService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class DirectMaterialCostServiceTest extends AbstractTest {

    @Autowired
    private DirectMaterialCostService directMaterialCostService;

    private static final String PROJECT = "N1568V R0";

    @Test
    public void a_createDirectMaterialCost() {
        directMaterialCostService.createDirectMaterialCost(PROJECT);
    }

    @Test
    public void b_computeDirectMaterialCost() {
        directMaterialCostService.computeDirectMaterialCost(PROJECT);
    }
}
