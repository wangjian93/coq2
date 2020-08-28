package com.ivo.test;

import com.ivo.test.coq.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 测试套件
 * @author wj
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataPrepare.class,
        ProjectServiceTest.class,
        DirectMaterialCostServiceTest.class,
        JigCostServiceTest.class,
        ObaCostServiceTest.class,
        SalaryCostServiceTest.class,
        TravelCostServiceTest.class,
        VerificationCostServiceTest.class,
        ProductionCostServiceTest.class,
        ReworkScrapSyncJobServiceTest.class,
        ReworkScrapCostServiceTest.class,
        CostServiceTest.class
})
public class Test {
}
