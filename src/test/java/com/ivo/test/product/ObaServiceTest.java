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
        obaService.computeObaMonth("202001");
        obaService.computeObaMonth("202002");
        obaService.computeObaMonth("202003");
        obaService.computeObaMonth("202004");
        obaService.computeObaMonth("202005");
        obaService.computeObaMonth("202006");
        obaService.computeObaMonth("202007");
        obaService.computeObaMonth("202008");
        obaService.computeObaMonth("202009");
        obaService.computeObaMonth("202010");
        obaService.computeObaMonth("202011");
        obaService.computeObaMonth("202012");
        obaService.computeObaMonth("202101");
        obaService.computeObaMonth("202102");
        obaService.computeObaMonth("202103");
        obaService.computeObaMonth("202104");
        obaService.computeObaMonth("202105");
        obaService.computeObaMonth("202106");
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
