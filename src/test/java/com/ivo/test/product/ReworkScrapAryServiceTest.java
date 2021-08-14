package com.ivo.test.product;

import com.ivo.product.service.ReworkScrapAryService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class ReworkScrapAryServiceTest extends AbstractTest {

    @Autowired
    private ReworkScrapAryService reworkScrapAryService;

    @Test
    public void syncAryReworkScrap() {
        reworkScrapAryService.syncAryReworkScrap("202106");
    }

    @Test
    public void computeAryReworkScrapAmount() {
        reworkScrapAryService.computeAryReworkScrapAmount("202106");
    }

    @Test
    public void computeReworkScrapMonth() {
        reworkScrapAryService.computeReworkScrapMonth("202106");
    }
}
