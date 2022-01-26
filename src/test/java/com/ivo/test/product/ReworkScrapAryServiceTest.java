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
    public void a_syncAryReworkScrap() {
        reworkScrapAryService.syncAryReworkScrap("202112");
    }

    @Test
    public void b_computeAryReworkScrapAmount() {
        reworkScrapAryService.computeAryReworkScrapAmount("202112");
    }

    @Test
    public void c_computeReworkScrapMonth() {
        reworkScrapAryService.computeReworkScrapMonth("202112");
    }
}
