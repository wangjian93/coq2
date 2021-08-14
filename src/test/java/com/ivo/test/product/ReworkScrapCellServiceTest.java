package com.ivo.test.product;

import com.ivo.product.service.ReworkScrapCellService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class ReworkScrapCellServiceTest extends AbstractTest {

    @Autowired
    private ReworkScrapCellService reworkScrapCellService;

    @Test
    public void syncCellReworkScrap() {
        reworkScrapCellService.syncCellReworkScrap("202106");
    }

    @Test
    public void computeCellReworkScrapAmount() {
        reworkScrapCellService.computeCellReworkScrapAmount("202106");
    }

    @Test
    public void computeReworkScrapMonth() {
        reworkScrapCellService.computeReworkScrapMonth("202106");
    }
}
