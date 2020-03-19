package com.ivo.test.coq.cost;

import com.ivo.coq.cost.service.CostService;
import com.ivo.coq.cost.service.CostStageService;
import com.ivo.coq.cost.service.CostSubjectService;
import com.ivo.coq.costCategory.compensation.service.CompensationCostSerivce;
import com.ivo.coq.costCategory.directMaterial.service.DirectMaterialCostService;
import com.ivo.coq.costCategory.jig.service.JigCostService;
import com.ivo.coq.costCategory.oba.service.ObaCostService;
import com.ivo.coq.costCategory.production.service.ProductionCostService;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostService;
import com.ivo.coq.costCategory.rma.service.RmaCostService;
import com.ivo.coq.costCategory.salary.service.SalaryCostService;
import com.ivo.coq.costCategory.systemMaintenance.serivce.SystemMaintenanceCostService;
import com.ivo.coq.costCategory.travel.service.TravelCostService;
import com.ivo.coq.costCategory.verification.service.VerificationCostService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class CostTest extends AbstractTest {

    @Autowired
    private CostService costService;

    @Autowired
    private CostSubjectService costSubjectService;

    @Autowired
    private CostStageService costStageService;

    @Autowired
    private CompensationCostSerivce compensationCostSerivce;

    @Autowired
    private DirectMaterialCostService directMaterialCostService;

    @Autowired
    private JigCostService jigCostService;

    @Autowired
    private VerificationCostService verificationCostService;

    @Autowired
    private ProductionCostService productionCostService;

    @Autowired
    private ReworkScrapCostService reworkScrapCostService;

    @Autowired
    private SalaryCostService salaryCostService;

    @Autowired
    private RmaCostService rmaCostService;

    @Autowired
    private ObaCostService obaCostService;

    @Autowired
    private TravelCostService travelCostService;

    @Autowired
    private SystemMaintenanceCostService systemMaintenanceCostService;

    private static String project1 = "N1339 R0";
    private static String project2 = "N1339 R1";

    @Test
    public void test() {
        compute("N1568V R0");
    }

    public void create(String project) {
        costService.createCost(project);
        costSubjectService.createCostSubject(project);
        costStageService.createCostStage(project);

        directMaterialCostService.createDirectMaterialCost(project);
        jigCostService.createJigCost(project);
        verificationCostService.crateVerificationCost(project);
        productionCostService.createProductionCost(project);
        reworkScrapCostService.createReworkScrapCost(project);
        salaryCostService.createSalaryCost(project);
        rmaCostService.createRmaCost(project);
        obaCostService.createObaCost(project);
        travelCostService.createTravelCost(project);
        systemMaintenanceCostService.createSystemMaintenanceCos(project);
        compensationCostSerivce.createCompensationCost(project);
    }

    public void compute(String project) {
        costStageService.computeCostStage(project);
        costSubjectService.computeCostSubject(project);
        costService.computeCost(project);
    }
}
