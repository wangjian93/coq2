package com.ivo.test.coq.cost;

import com.ivo.coq.costCategory.directMaterial.service.MaterialCostDetailService;
import com.ivo.test.AbstractTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * @author wj
 * @version 1.0
 */
public class MaterialCostDetailServiceTest extends AbstractTest {

    @Autowired
    private MaterialCostDetailService materialCostDetailService;

    private static final String PROJECT = "N1568V R0";

    /**
     * 创建直材费用
     */
    @Test
    public void a_createMaterialCostDetail() {
        materialCostDetailService.createMaterialCostDetail(PROJECT);
    }

    /**
     * 同步Array厂的材料单片费用
     */
    @Test
    public void b_syncArrayProductAmount() {
        materialCostDetailService.syncArrayProductAmount(PROJECT);
    }

    /**
     * 同步LCM厂的工单费用
     */
    @Test
    public void c_syncLcmWoAmount() {
        materialCostDetailService.syncLcmWoAmount(PROJECT);
    }

    /**
     * 同步CELL厂的料号单价
     */
    @Test
    public void d_syncCellMaterialAmount() {
        materialCostDetailService.syncCellMaterialAmount(PROJECT);
    }

    /**
     * 计算厂内直材费用
     */
    @Test
    public void e_computeMaterialCostDetail() {
        materialCostDetailService.computeMaterialCostDetail(PROJECT);
    }
}
