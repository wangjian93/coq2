package com.ivo.test.product;

import com.ivo.product.service.MardService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author wj
 * @version 1.0
 */
public class MardServiceTest extends AbstractTest {

    @Autowired
    private MardService mardService;

    @Test
    public void syncMard() {
        mardService.syncMard("202103");
        mardService.syncMard("202102");
        mardService.syncMard("202101");
    }

    @Test
    public void syncMatnrPrice() {
        mardService.syncMatnrPrice("202103");
        mardService.syncMatnrPrice("202102");
        mardService.syncMatnrPrice("202101");
    }

    @Test
    public void computeMardMonth() {
        mardService.computeMardMonth("202104");
        mardService.computeMardMonth("202103");
        mardService.computeMardMonth("202102");
        mardService.computeMardMonth("202101");
    }
}
