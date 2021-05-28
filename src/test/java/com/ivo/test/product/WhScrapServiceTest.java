package com.ivo.test.product;

import com.ivo.product.service.WhScrapService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class WhScrapServiceTest extends AbstractTest {

    @Autowired
    private WhScrapService whScrapService;

    @Test
    public void syncWhScrap() {
        whScrapService.syncWhScrap();
    }

    @Test
    public void computeWhScrapMonth() {
        whScrapService.computeWhScrapMonth("202104");
        whScrapService.computeWhScrapMonth("202103");
        whScrapService.computeWhScrapMonth("202102");
        whScrapService.computeWhScrapMonth("202101");
    }
}
