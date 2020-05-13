package com.ivo.test.coq;

import com.ivo.coq.costCategory.directMaterial.service.DirectMaterialCostService;
import com.ivo.coq.costCategory.directMaterial.service.MaterialCostDetailService;
import com.ivo.coq.costCategory.directMaterial.service.OutsourcingThinningCostDetailService;
import com.ivo.coq.costCategory.directMaterial.service.ShipmentCostDetailService;
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
public class DirectMaterialCostServiceTest extends AbstractTest {

    @Autowired
    private DirectMaterialCostService directMaterialCostService;

    @Autowired
    private MaterialCostDetailService materialCostDetailService;

    @Autowired
    private OutsourcingThinningCostDetailService outsourcingThinningCostDetailService;

    @Autowired
    private ShipmentCostDetailService shipmentCostDetailService;

    /**
     * 创建机种的直接材料费用
     */
    @Test
    public void a1_createDirectMaterialCost() {
        directMaterialCostService.createDirectMaterialCost(PROJECT);
    }

    /**
     * 创建直材费用
     */
    @Test
    public void b1_createMaterialCostDetail() {
        materialCostDetailService.createMaterialCostDetail(PROJECT);
    }

    /**
     * 同步Array厂的材料单片费用
     */
    @Test
    public void b2_syncArrayProductAmount() {
        materialCostDetailService.syncArrayProductAmount(PROJECT);
    }

    /**
     * 同步LCM厂的工单费用
     */
    @Test
    public void b3_syncLcmWoAmount() {
        materialCostDetailService.syncLcmWoAmount(PROJECT);
    }

    /**
     * 同步CELL厂的料号单价
     */
    @Test
    public void b4_syncCellMaterialAmount() {
        materialCostDetailService.syncCellMaterialAmount(PROJECT);
    }

    /**
     * 计算厂内直材费用
     */
    @Test
    public void b5_computeMaterialCostDetail() {
        materialCostDetailService.computeMaterialCostDetail(PROJECT);
    }


    /**
     * 同步获取机种外包薄化费用
     */
    @Test
    public void c1_syncOutsourcingThinningCostDetail() {
        outsourcingThinningCostDetailService.syncOutsourcingThinningCostDetail(PROJECT);
    }

    /**
     * 创建出货费用
     */
    @Test
    public void d1_createShipmentCostDetail() {
        shipmentCostDetailService.createShipmentCostDetail(PROJECT);
    }

    /**
     * 计算出货费用
     */
    @Test
    public void d2_computeShipmentCostDetail() {
        shipmentCostDetailService.computeShipmentCostDetail(PROJECT);
    }

    /**
     * 计算机种的直接材料费用
     */
    @Test
    public void e1_computeDirectMaterialCost() {
        directMaterialCostService.computeDirectMaterialCost(PROJECT);
    }
}
