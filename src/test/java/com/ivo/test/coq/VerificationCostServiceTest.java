package com.ivo.test.coq;

import com.ivo.coq.costCategory.verification.service.VerificationCostBmDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationCostPlantDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationCostService;
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
public class VerificationCostServiceTest extends AbstractTest {

    @Autowired
    private VerificationCostService verificationCostService;

    @Autowired
    private VerificationCostBmDetailService bmDetailService;

    @Autowired
    private VerificationCostPlantDetailService plantDetail;

    /**
     * 创建机种的验证费用
     */
    @Test
    public void a1_crateVerificationCost() {
        verificationCostService.crateVerificationCost(PROJECT);
    }

    /**
     * 同步获取机种的BM验证费用
     */
    @Test
    public void a2_syncVerificationCostBmDetail() {
        bmDetailService.syncVerificationCostBmDetail(PROJECT);
    }

    /**
     * 从QMS同步机种的厂内验证信息
     */
    @Test
    public void a3_syncVerificationCostPlantDetail() {
        plantDetail.syncVerificationCostPlantDetail(PROJECT);
    }

    /**
     * 计算机种的厂内验证费用
     */
    @Test
    public void a4_computeVerificationCostPlantDetail() {
        plantDetail.computeVerificationCostPlantDetail(PROJECT);
    }

    /**
     * 计算机种的验证费用
     */
    @Test
    public void a5_computeVerificationCost() {
        verificationCostService.computeVerificationCost(PROJECT);
    }
}
