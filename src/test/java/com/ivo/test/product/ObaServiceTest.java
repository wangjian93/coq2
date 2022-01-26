package com.ivo.test.product;

import com.ivo.product.service.LocationCostService;
import com.ivo.product.service.ObaService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class ObaServiceTest extends AbstractTest {

    @Autowired
    private ObaService obaService;

    @Autowired
    private LocationCostService locationCostService;

    @Test
    public void syncOba() {
        obaService.syncOba();
    }


    @Test
    public void syncFab() {
        obaService.syncFab();
    }

    @Test
    public void computeObaMonth() {
        obaService.computeObaMonth("202111");
    }

    @Test
    public void syncLocationCost() {
        locationCostService.syncLocationCost();
    }

    @Test
    public void syncScrapDetail() {
        locationCostService.syncScrapDetail();
    }
}
