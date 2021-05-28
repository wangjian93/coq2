package com.ivo.test.product;

import com.ivo.product.service.WoCloseService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class WoCloseServiceTest extends AbstractTest {

    @Autowired
    private WoCloseService woCloseService;

    @Test
    public void syncWoClose() {
        woCloseService.syncWoClose("202103");
        woCloseService.syncWoClose("202102");
        woCloseService.syncWoClose("202101");
    }

    @Test
    public void syncMatnrPrice() {
        woCloseService.syncMatnrPrice("202103");
        woCloseService.syncMatnrPrice("202102");
        woCloseService.syncMatnrPrice("202101");
    }

    @Test
    public void computeWoCloseMonth() {
        woCloseService.computeWoCloseMonth("202103");
        woCloseService.computeWoCloseMonth("202102");
        woCloseService.computeWoCloseMonth("202101");
    }
}
