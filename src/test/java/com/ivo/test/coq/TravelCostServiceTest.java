package com.ivo.test.coq;

import com.ivo.coq.costCategory.travel.service.TravelCostDetailService;
import com.ivo.coq.costCategory.travel.service.TravelCostService;
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
public class TravelCostServiceTest extends AbstractTest {

    @Autowired
    private TravelCostService travelCostService;

    @Autowired
    private TravelCostDetailService travelCostDetailService;

    private static final String PROJECT = "N1568V R0";

    /**
     * 创建机种的差旅交际费用
     */
    @Test
    public void a1_createTravelCost() {
        travelCostService.createTravelCost(PROJECT);
    }

    /**
     * 从出差报支单同步差旅信息
     */
    @Test
    public void a2_syncTravelCostDetail() {
        travelCostDetailService.syncTravelCostDetail(PROJECT);
    }

    /**
     * 计算机种的差旅交际费用
     */
    @Test
    public void a3_computeTravelCost() {
        travelCostService.computeTravelCost(PROJECT);
    }
}
