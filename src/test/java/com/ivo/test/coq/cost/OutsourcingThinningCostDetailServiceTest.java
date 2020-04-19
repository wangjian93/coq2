package com.ivo.test.coq.cost;

import com.ivo.coq.costCategory.directMaterial.service.OutsourcingThinningCostDetailService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class OutsourcingThinningCostDetailServiceTest extends AbstractTest {

    @Autowired
    private OutsourcingThinningCostDetailService outsourcingThinningCostDetailService;

    private static final String PROJECT = "N1568V R0";

    @Test
    public void a_syncOutsourcingThinningCostDetail() {
        outsourcingThinningCostDetailService.syncOutsourcingThinningCostDetail(PROJECT);
    }
}
