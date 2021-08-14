package com.ivo.test;

import com.ivo.coq.cost.service.CostService;
import com.ivo.coq.cost.service.CostStageService;
import com.ivo.coq.cost.service.CostSubjectService;
import com.ivo.coq.costCategory.directMaterial.service.DirectMaterialCostService;
import com.ivo.coq.costCategory.directMaterial.service.MaterialCostDetailService;
import com.ivo.coq.costCategory.directMaterial.service.OutsourcingThinningCostDetailService;
import com.ivo.coq.costCategory.directMaterial.service.ShipmentCostDetailService;
import com.ivo.coq.costCategory.jig.service.JigCostDetailService;
import com.ivo.coq.costCategory.jig.service.JigCostService;
import com.ivo.coq.costCategory.oba.service.ObaCostDetailService;
import com.ivo.coq.costCategory.oba.service.ObaCostService;
import com.ivo.coq.costCategory.production.service.ProductionCostDetailService;
import com.ivo.coq.costCategory.production.service.ProductionCostService;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostDetailService;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostService;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapSyncJobService;
import com.ivo.coq.costCategory.salary.service.SalaryCostDetailService;
import com.ivo.coq.costCategory.salary.service.SalaryCostNormalHoursDetailService;
import com.ivo.coq.costCategory.salary.service.SalaryCostService;
import com.ivo.coq.costCategory.travel.service.TravelCostDetailService;
import com.ivo.coq.costCategory.travel.service.TravelCostService;
import com.ivo.coq.costCategory.verification.service.VerificationCostBmDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationCostPlantDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationCostService;
import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.service.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test2 extends AbstractTest {

    @Autowired
    ProjectService projectService;

    @Autowired
    private SampleService sampleService;
    @Autowired
    private MilestoneService milestoneService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private StageService stageService;
    @Autowired
    private EngineeringExperimentService engineeringExperimentService;

    @Autowired
    private DirectMaterialCostService directMaterialCostService;
    @Autowired
    private MaterialCostDetailService materialCostDetailService;
    @Autowired
    private OutsourcingThinningCostDetailService outsourcingThinningCostDetailService;
    @Autowired
    private ShipmentCostDetailService shipmentCostDetailService;

    @Autowired
    private JigCostService jigCostService;
    @Autowired
    private JigCostDetailService jigCostDetailService;

    @Autowired
    private ObaCostService obaCostService;
    @Autowired
    private ObaCostDetailService obaCostDetailService;

    @Autowired
    private SalaryCostService salaryCostService;
    @Autowired
    private SalaryCostDetailService salaryCostDetailService;
    @Autowired
    private SalaryCostNormalHoursDetailService salaryCostNormalHoursDetailService;

    @Autowired
    private TravelCostService travelCostService;
    @Autowired
    private TravelCostDetailService travelCostDetailService;

    @Autowired
    private VerificationCostService verificationCostService;
    @Autowired
    private VerificationCostBmDetailService bmDetailService;
    @Autowired
    private VerificationCostPlantDetailService plantDetail;

    @Autowired
    private ProductionCostDetailService productionCostDetailService;
    @Autowired
    private ProductionCostService productionCostService;

    @Autowired
    private ReworkScrapSyncJobService reworkScrapSyncJobService;

    @Autowired
    private ReworkScrapCostService reworkScrapCostService;
    @Autowired
    private ReworkScrapCostDetailService detailService;

    @Autowired
    private CostService costService;
    @Autowired
    private CostStageService costStageService;
    @Autowired
    private CostSubjectService costSubjectService;

    @Test
    public void test() {
//        List<Project> projectList = projectService.getProjects();
//        for(Project p : projectList) {
//            String project = p.getProject();
//            run(project);
//        }
//
        run("A0962 R0");
    }

    public void run(String PROJECT) {
        System.out.println("计算机种" + PROJECT);
//        projectServiceTest(PROJECT);
        directMaterialCostServiceTest(PROJECT);
        JigCostServiceTest(PROJECT);
        ObaCostServiceTest(PROJECT);
        SalaryCostServiceTest(PROJECT);
        TravelCostServiceTest(PROJECT);
        VerificationCostServiceTest(PROJECT);
        ProductionCostServiceTest(PROJECT);
        //ReworkScrapSyncJobServiceTest(PROJECT);
        //ReworkScrapCostServiceTest(PROJECT);
        CostServiceTest(PROJECT);
    }

    public void projectServiceTest(String PROJECT) {
        sampleService.syncSamples(PROJECT);
        milestoneService.syncMilestone(PROJECT);
        memberService.syncMember(PROJECT);
        stageService.generateStage(PROJECT);
        engineeringExperimentService.generateEngineeringExperiment(PROJECT);
        engineeringExperimentService.syncEngineeringExperimentPlant(PROJECT);
        engineeringExperimentService.syncEngineeringExperimentProduct(PROJECT);
        engineeringExperimentService.syncEngineeringExperimentMaterial(PROJECT);
        engineeringExperimentService.syncEngineeringExperimentWo(PROJECT);
    }

    public void directMaterialCostServiceTest(String PROJECT) {
        directMaterialCostService.createDirectMaterialCost(PROJECT);
        materialCostDetailService.createMaterialCostDetail(PROJECT);
        materialCostDetailService.syncArrayProductAmount(PROJECT);
        materialCostDetailService.syncLcmWoAmount(PROJECT);
        materialCostDetailService.syncCellMaterialAmount(PROJECT);
        materialCostDetailService.computeMaterialCostDetail(PROJECT);
        outsourcingThinningCostDetailService.syncOutsourcingThinningCostDetail(PROJECT);
        shipmentCostDetailService.createShipmentCostDetail(PROJECT);
        shipmentCostDetailService.computeShipmentCostDetail(PROJECT);
        directMaterialCostService.computeDirectMaterialCost(PROJECT);
    }

    public void JigCostServiceTest(String PROJECT) {
        jigCostService.createJigCost(PROJECT);
        jigCostDetailService.syncJigCostDetail(PROJECT);
        jigCostService.computeJigCost(PROJECT);
    }

    public void ObaCostServiceTest(String PROJECT) {
        obaCostService.createObaCost(PROJECT);
        obaCostDetailService.syncObaCostDetail(PROJECT);
        obaCostService.computeObaCost(PROJECT);
    }

    public void SalaryCostServiceTest(String PROJECT) {
        salaryCostService.createSalaryCost(PROJECT);
        salaryCostDetailService.syncSalaryCostDetail(PROJECT);
        salaryCostNormalHoursDetailService.syncSalaryCostNormalHoursDetail(PROJECT);
        salaryCostService.computeSalaryCost(PROJECT);
    }

    public void TravelCostServiceTest(String PROJECT) {
        travelCostService.createTravelCost(PROJECT);
        travelCostDetailService.syncTravelCostDetail(PROJECT);
        travelCostService.computeTravelCost(PROJECT);
    }

    public void VerificationCostServiceTest(String PROJECT) {
        verificationCostService.crateVerificationCost(PROJECT);
        bmDetailService.syncVerificationCostBmDetail(PROJECT);
        plantDetail.syncVerificationCostPlantDetail(PROJECT);
        plantDetail.computeVerificationCostPlantDetail(PROJECT);
        verificationCostService.computeVerificationCost(PROJECT);
    }

    public void ProductionCostServiceTest(String PROJECT) {
        productionCostDetailService.syncProductionCostDetail(PROJECT);
        productionCostDetailService.computeProductionCostDetail(PROJECT);
        productionCostService.createProductionCost(PROJECT);
        productionCostService.computeProductionCost(PROJECT);
    }

    public void ReworkScrapSyncJobServiceTest(String PROJECT) {
        reworkScrapSyncJobService.generateJob(PROJECT);
        reworkScrapSyncJobService.runJob(PROJECT);
    }

    public void ReworkScrapCostServiceTest(String PROJECT) {
        detailService.computeReworkScrapCostDetail(PROJECT);
        reworkScrapCostService.createReworkScrapCost(PROJECT);
        reworkScrapCostService.computeReworkScrapCost(PROJECT);
    }

    public void CostServiceTest(String PROJECT) {
        costService.createCost(PROJECT);
        costStageService.createCostStage(PROJECT);
        costSubjectService.createCostSubject(PROJECT);
        costSubjectService.computeCostSubject(PROJECT);
        costStageService.computeCostStage(PROJECT);
        costService.computeCost(PROJECT);
    }
}
