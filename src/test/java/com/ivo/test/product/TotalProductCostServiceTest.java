package com.ivo.test.product;

import com.ivo.product.service.TotalProductCostService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class TotalProductCostServiceTest extends AbstractTest {

    @Autowired
    private TotalProductCostService totalProductCostService;

    @Test
    public void test() {
        totalProductCostService.syncTotalProductCost();
    }
}
