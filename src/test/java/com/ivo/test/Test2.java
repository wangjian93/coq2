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
import com.ivo.coq.costCategory.salary.entity.DesignWorkDay;
import com.ivo.coq.costCategory.salary.service.SalaryCostDetailService;
import com.ivo.coq.costCategory.salary.service.SalaryCostNormalHoursDetailService;
import com.ivo.coq.costCategory.salary.service.SalaryCostService;
import com.ivo.coq.costCategory.travel.service.TravelCostDetailService;
import com.ivo.coq.costCategory.travel.service.TravelCostService;
import com.ivo.coq.costCategory.verification.service.VerificationCostBmDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationCostPlantDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationCostService;
import com.ivo.coq.project.entity.Member;
import com.ivo.coq.project.entity.Milestone;
import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void test2() throws ParseException {
        String[] msgs = new String[] {
                "PLM数据未维护", "Time不符合", "实验申请单不符合"
        };
        class E{
            private String project;
            private String stage;
            private String pm;
            private String msg;
            private String ds;
            private Date endDate;
            public E(String project, String stage, String pm, String msg, String ds, Date endDate) {
                this.project = project;
                this.stage = stage;
                this.pm = pm;
                this.msg = msg;
                this.ds = ds;
                this.endDate =endDate;
            }

            public String getProject() {
                return this.project;
            }
            public String getStage() {
                return this.stage;
            }
            public String getPm() {
                return this.pm;
            }
            public String getMsg() {
                return this.msg;
            }
            public String getDs() {
                return this.ds;
            }
            public Date getEndDate() {
                return this.endDate;
            }

        }

        List<E> list = new ArrayList<>();
        List<Project> projectList = projectService.getProjects();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Project p : projectList) {
            String project = p.getProject();
            List<Milestone> milestoneList = milestoneService.getMilestone(project);
            Date date = sdf.parse("2020-01-01");
            Date endDate = null;
            for(Milestone milestone : milestoneList) {
                if(milestone.getMilestone().equals("NPRB")) {
                    endDate = milestone.getEndDate();
                }
            }
            if(endDate != null && endDate.before(date)) continue;

            List<Sample> sampleList = sampleService.getSamples(project);
            List<Member> memberList = memberService.getMembers(project, "PJM");
            String member = "";
            if(memberList.size()>0) {
                member = memberList.get(0).getEmployeeId() + "/" + memberList.get(0).getEmployeeName();
            }

            List<String> stageList = new ArrayList<>();
            for(Sample sample : sampleList) {
                Integer time = sample.getTime();
                String orderNumber = sample.getOrderNumber();
                String stage = sample.getStage();
                if(time == null || time<1 || time >99) {
                    list.add(new E(project, stage, member, msgs[1], "Time="+time, endDate));
                }
                if(!StringUtils.startsWithAny(orderNumber, "ED", "EE", "OEE")) {
                    list.add(new E(project, stage, member, msgs[2], "EE/ED="+orderNumber, endDate));
                }
                stageList.add(stage);
            }

            for(Milestone milestone : milestoneList) {
                String m = milestone.getMilestone();
                if(!StringUtils.containsAny(m, "NPRB", "Design", "EVT", "DVT", "MP") ) continue;
                if(StringUtils.containsIgnoreCase(m, "Design")) m = "Design";
                if(stageList.contains(m)) continue;
                list.add(new E(project, m, member, msgs[0], "", endDate));
            }
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        int intRow =0;
        int intCel = 0;
        String[] titleItems = new String[] {"机种", "阶段", "PJM", "异常", "描述", "日期"};
        Row row1 = sheet.createRow(intRow);
        for(; intCel<titleItems.length; intCel++) {
            Cell cell = row1.createCell(intCel);
            cell.setCellValue(titleItems[intCel]);
        }
        for(E e : list) {
            intRow++;
            intCel = 0;
            Row row = sheet.createRow(intRow);
            row.createCell(intCel++).setCellValue(e.getProject());
            row.createCell(intCel++).setCellValue(e.getStage());
            row.createCell(intCel++).setCellValue(e.getPm());
            row.createCell(intCel++).setCellValue(e.getMsg());
            row.createCell(intCel++).setCellValue(e.getDs());
            row.createCell(intCel++).setCellValue(sdf.format(e.getEndDate()));
        }

        try {
            FileOutputStream outputStream = new FileOutputStream("/Users/wangjian/Downloads/ttttt3.xlsx");
            workbook.write(outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        List<Project> projectList = projectService.getProjects();
        for(Project p : projectList) {
            String project = p.getProject();
            run(project);
        }

//        String[] projects = new String[] {"A0906 R0", "A0906 R1"};
//        for(String project : projects) {
//            run(project);
//        }

//                    run("N1601H R0");

    }

    public void run(String PROJECT) {
        System.out.println("计算机种" + PROJECT);
////        projectServiceTest(PROJECT);
//        directMaterialCostServiceTest(PROJECT);
        JigCostServiceTest(PROJECT);
//        ObaCostServiceTest(PROJECT);
//        SalaryCostServiceTest(PROJECT);
////        TravelCostServiceTest(PROJECT);
//        VerificationCostServiceTest(PROJECT);
//        ProductionCostServiceTest(PROJECT);
////        //ReworkScrapSyncJobServiceTest(PROJECT);
////        //ReworkScrapCostServiceTest(PROJECT);
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
