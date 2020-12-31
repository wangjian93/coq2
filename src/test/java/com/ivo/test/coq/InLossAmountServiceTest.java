package com.ivo.test.coq;

import com.ivo.coq.report.service.InLossAmountService;
import com.ivo.coq.report.service.TotalAmountService;
import com.ivo.coq.report.service.WbRatioService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class InLossAmountServiceTest extends AbstractTest {

    @Autowired
    private InLossAmountService inLossAmountService;

    @Autowired
    private TotalAmountService totalAmountService;

    @Autowired
    private WbRatioService wbRatioService;

    @Test
    public void syncInLossAmount() {
        inLossAmountService.syncInLossAmount();
    }

    @Test
    public void syncInLossAmountDetailArrayCell(){
        inLossAmountService.syncInLossAmountDetailArrayCell();
    }

    @Test
    public void syncInLossAmountDetailLcm(){
        inLossAmountService.syncInLossAmountDetailLcm();
    }

    @Test
    public void syncTotalAmount() {
        totalAmountService.syncTotalAmount();
    }

    @Test
    public void syncWbRatio() {
        wbRatioService.syncWbRatio();
    }

}
