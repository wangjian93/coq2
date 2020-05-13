package com.ivo.test.coq;

import com.ivo.coq.cost.service.CostService;
import com.ivo.coq.cost.service.CostStageService;
import com.ivo.coq.cost.service.CostSubjectService;
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
public class CostServiceTest extends AbstractTest {

    @Autowired
    private CostService costService;

    @Autowired
    private CostStageService costStageService;

    @Autowired
    private CostSubjectService costSubjectService;

    /**
     * 创建Cost
     */
    @Test
    public void a1_createCost() {
        costService.createCost(PROJECT);
    }

    /**
     * 创建机种的阶段成本
     */
    @Test
    public void a2_createCostStage() {
        costStageService.createCostStage(PROJECT);
    }


    /**
     * 创建机种机种的二级科目
     */
    @Test
    public void a3_createCostSubject() {
        costSubjectService.createCostSubject(PROJECT);
    }

    /**
     * 计算机种的二级科目
     */
    @Test
    public void b1_computeCostSubject() {
        costSubjectService.computeCostSubject(PROJECT);
    }

    /**
     * 计算机种的阶段成本
     */
    @Test
    public void b2_computeCostStage() {
        costStageService.computeCostStage(PROJECT);
    }


    /**
     * 计算Cost
     */
    @Test
    public void b3_computeCost() {
        costService.computeCost(PROJECT);
    }
}
