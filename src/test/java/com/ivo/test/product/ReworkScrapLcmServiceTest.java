package com.ivo.test.product;

import com.ivo.product.service.ReworkScrapLcmService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author wj
 * @version 1.0
 */
public class ReworkScrapLcmServiceTest extends AbstractTest {

    @Autowired
    private ReworkScrapLcmService reworkScrapLcmService;

    @Test
    public void syncReworkScrapLcm() {
        reworkScrapLcmService.syncReworkScrap("202106");
    }

    @Test
    public void syncPerReworkScrapAmount() {
        reworkScrapLcmService.syncPerReworkScrapAmountLcm("202106");
    }

    @Test
    public void computeReworkScrapMonth() {
        reworkScrapLcmService.computeReworkScrapMonth("202106");
    }
}
