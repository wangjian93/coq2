package com.ivo.coq.cost.service.impl;

import com.ivo.coq.cost.entity.CostStage;
import com.ivo.coq.cost.repository.CostStageRepository;
import com.ivo.coq.cost.service.CostStageService;
import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.compensation.entity.CompensationCost;
import com.ivo.coq.costCategory.compensation.service.CompensationCostSerivce;
import com.ivo.coq.costCategory.directMaterial.entity.DirectMaterialCost;
import com.ivo.coq.costCategory.directMaterial.service.DirectMaterialCostService;
import com.ivo.coq.costCategory.jig.entity.JigCost;
import com.ivo.coq.costCategory.jig.service.JigCostService;
import com.ivo.coq.costCategory.oba.entity.ObaCost;
import com.ivo.coq.costCategory.oba.service.ObaCostService;
import com.ivo.coq.costCategory.production.entity.ProductionCost;
import com.ivo.coq.costCategory.production.service.ProductionCostService;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCost;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostService;
import com.ivo.coq.costCategory.rma.entity.RmaCost;
import com.ivo.coq.costCategory.rma.service.RmaCostService;
import com.ivo.coq.costCategory.salary.entity.SalaryCost;
import com.ivo.coq.costCategory.salary.service.SalaryCostService;
import com.ivo.coq.costCategory.systemMaintenance.entity.SystemMaintenanceCost;
import com.ivo.coq.costCategory.systemMaintenance.serivce.SystemMaintenanceCostService;
import com.ivo.coq.costCategory.travel.entity.TravelCost;
import com.ivo.coq.costCategory.travel.service.TravelCostService;
import com.ivo.coq.costCategory.verification.entity.VerificationCost;
import com.ivo.coq.costCategory.verification.service.VerificationCostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class CostStageServiceImpl implements CostStageService {

    private static Logger logger = LoggerFactory.getLogger(CostStageServiceImpl.class);

    private CostStageRepository repository;

    private ProjectService projectService;

    private SalaryCostService salaryCostService;
    private TravelCostService travelCostService;
    private JigCostService jigCostService;
    private SystemMaintenanceCostService systemMaintenanceCostService;
    private DirectMaterialCostService directMaterialCostService;
    private ProductionCostService productionCostService;
    private VerificationCostService verificationCostService;
    private ReworkScrapCostService reworkScrapCostService;
    private ObaCostService obaCostService;
    private RmaCostService rmaCostService;
    private CompensationCostSerivce compensationCostSerivce;

    @Autowired
    public CostStageServiceImpl(CostStageRepository repository, ProjectService projectService,
                                SalaryCostService salaryCostService, TravelCostService travelCostService,
                                JigCostService jigCostService, SystemMaintenanceCostService systemMaintenanceCostService,
                                DirectMaterialCostService directMaterialCostService,
                                ProductionCostService productionCostService,
                                VerificationCostService verificationCostService,
                                ReworkScrapCostService reworkScrapCostService, ObaCostService obaCostService,
                                RmaCostService rmaCostService, CompensationCostSerivce compensationCostSerivce) {
        this.repository = repository;
        this.projectService = projectService;
        this.salaryCostService = salaryCostService;
        this.travelCostService = travelCostService;
        this.jigCostService = jigCostService;
        this.systemMaintenanceCostService = systemMaintenanceCostService;
        this.directMaterialCostService = directMaterialCostService;
        this.productionCostService = productionCostService;
        this.verificationCostService = verificationCostService;
        this.reworkScrapCostService = reworkScrapCostService;
        this.obaCostService = obaCostService;
        this.rmaCostService = rmaCostService;
        this.compensationCostSerivce = compensationCostSerivce;
    }

    @Override
    public List<CostStage> getCostStages(String project) {
        return repository.findByProject(project);
    }

    @Override
    public void computeCostStage(String project) {
        // TODO...
        logger.info("计算CostStage >> " + project);
        List<CostStage> costStageList = getCostStages(project);
        for(CostStage costStage : costStageList) {
            String stage = costStage.getStage();
            Integer time = costStage.getTime();

            // 直接材料
            DirectMaterialCost directMaterialCost = directMaterialCostService.getDirectMaterialCost(project, stage, time);
            costStage.setDirectMaterialCost(directMaterialCost.getAmount());

            // 治工具
            JigCost jigCost = jigCostService.getJigCost(project, stage, time);
            costStage.setJigCost(jigCost.getAmount());

            // 验证费用
            VerificationCost verificationCost = verificationCostService.getVerificationCost(project, stage, time);
            costStage.setVerificationCost(verificationCost.getAmount());

            // 生产费用
            ProductionCost productionCost = productionCostService.getProductionCost(project, stage, time);
            costStage.setProductionCost(productionCost.getAmount());

            // 重工/报废费用
            ReworkScrapCost reworkScrapCost = reworkScrapCostService.getReworkScrapCost(project, stage, time);
            costStage.setReworkScrapCost(reworkScrapCost.getAmount());

            // 人员薪资
            SalaryCost salaryCost = salaryCostService.getSalaryCost(project, stage, time);
            costStage.setSalaryCost(salaryCost.getAmount());

            // RMA
            RmaCost rmaCost = rmaCostService.getRmaCost(project, stage, time);
            costStage.setRmaCost(rmaCost.getAmount());

            // OBA
            ObaCost obaCost = obaCostService.getObaCost(project, stage, time);
            costStage.setObaCost(obaCost.getAmount());

            // 差旅费
            TravelCost travelCost = travelCostService.getTravelCost(project, stage, time);
            costStage.setTravelCost(travelCost.getAmount());

            // 系统维护折旧费
            SystemMaintenanceCost systemMaintenanceCost = systemMaintenanceCostService.getSystemMaintenanceCost(project, stage, time);
            costStage.setSystemMaintenanceCost(systemMaintenanceCost.getAmount());

            // 赔偿费用
            CompensationCost compensationCost = compensationCostSerivce.getCompensationCost(project, stage, time);
            costStage.setCompensationCost(compensationCost.getAmount());
        }

        repository.saveAll(costStageList);
    }

    @Override
    public void createCostStage(String project) {
        logger.info("创建 CostStage >>" + project);
        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        List<CostStage> costStageList = new ArrayList<>();
        for(ProjectStage projectStage : projectStageList) {
            CostStage costStage = new CostStage(projectStage.getProject(), projectStage.getStage(), projectStage.getTime());
            String stage = costStage.getStage();

            // 1. NPRB: 人员工资费用、差旅交际费 （2个）
            if(stage.equals("NPRB")) {
                costStage.setDirectMaterialCost(-1D);
                costStage.setJigCost(-1D);
                costStage.setVerificationCost(-1D);
                costStage.setProductionCost(-1D);
                costStage.setReworkScrapCost(-1D);
                costStage.setRmaCost(-1D);
                costStage.setObaCost(-1D);
                costStage.setCompensationCost(-1D);
                costStage.setSystemMaintenanceCost(-1D);
            }
            // 2. DESIGN: 人员工资费用、治工具费用、系统维护折旧费用 （3个）
            if(stage.equals("DESIGN")) {
                costStage.setDirectMaterialCost(-1D);
                costStage.setVerificationCost(-1D);
                costStage.setProductionCost(-1D);
                costStage.setReworkScrapCost(-1D);

                costStage.setTravelCost(-1D);
                costStage.setRmaCost(-1D);
                costStage.setObaCost(-1D);
                costStage.setCompensationCost(-1D);
            }
            // 3. EVT/DVT/PVT: 直接材料费用、治工具费用、验证费用、生产费用、重工报废费用、RMA费用、OBA费用、赔偿费用 （8个）
            if(stage.equals("EVT") || stage.equals("DVT") || stage.equals("PVT") ) {

                costStage.setSalaryCost(-1D);
                costStage.setTravelCost(-1D);
                costStage.setSystemMaintenanceCost(-1D);
            }
            // 4. MP: 重工报废费用、差旅交际费用、RMA费用、OBA费用、赔偿费用 （3个）
            if(stage.equals("MP")) {
                costStage.setDirectMaterialCost(-1D);
                costStage.setJigCost(-1D);
                costStage.setVerificationCost(-1D);
                costStage.setProductionCost(-1D);
                costStage.setSalaryCost(-1D);
                costStage.setTravelCost(-1D);
                costStage.setSystemMaintenanceCost(-1D);
            }

            costStageList.add(costStage);
        }

        repository.saveAll(costStageList);
    }
}
