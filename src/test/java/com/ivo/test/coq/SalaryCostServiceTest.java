package com.ivo.test.coq;

import com.ivo.coq.costCategory.salary.service.SalaryCostDetailService;
import com.ivo.coq.costCategory.salary.service.SalaryCostNormalHoursDetailService;
import com.ivo.coq.costCategory.salary.service.SalaryCostService;
import com.ivo.test.AbstractTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author wj
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalaryCostServiceTest extends AbstractTest {

    @Autowired
    private SalaryCostService salaryCostService;

    @Autowired
    private SalaryCostDetailService salaryCostDetailService;

    @Autowired
    private SalaryCostNormalHoursDetailService salaryCostNormalHoursDetailService;

    private static final String PROJECT = "N1568V R0";

    @Test
    public void a1_createSalaryCost() {
        salaryCostService.createSalaryCost(PROJECT);
    }

    public void a2_() {

    }

    public void a3_() {

    }

    public void a4_() {

    }
}
