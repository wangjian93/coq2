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
//        mardService.syncMard("202106");
    }

    @Test
    public void syncMatnrPrice() {
        mardService.syncMatnrPrice("202106");
    }

    @Test
    public void computeMardMonth() {
        mardService.computeMardMonth("202106");
    }
}
