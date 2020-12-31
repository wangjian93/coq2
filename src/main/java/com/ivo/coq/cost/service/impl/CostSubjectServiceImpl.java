package com.ivo.coq.cost.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.cost.entity.CostSubject;
import com.ivo.coq.cost.repository.CostSubjectRepository;
import com.ivo.coq.cost.service.CostSubjectService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class CostSubjectServiceImpl implements CostSubjectService {

    private CostSubjectRepository repository;

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
    public CostSubjectServiceImpl(CostSubjectRepository repository,
                                  SalaryCostService salaryCostService, TravelCostService travelCostService,
                                  JigCostService jigCostService, SystemMaintenanceCostService systemMaintenanceCostService,
                                  DirectMaterialCostService directMaterialCostService,
                                  ProductionCostService productionCostService,
                                  VerificationCostService verificationCostService,
                                  ReworkScrapCostService reworkScrapCostService,
                                  ObaCostService obaCostService,
                                  RmaCostService rmaCostService,
                                  CompensationCostSerivce compensationCostSerivce) {
        this.repository = repository;
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
    public List<CostSubject> getCostSubjects(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public void createCostSubject(String project) {
        log.info("创建 CostSubject >>" + project);
        repository.deleteAll(getCostSubjects(project));
        List<CostSubject> costSubjectList = new ArrayList<>();
        costSubjectList.add(new CostSubject(project, "NPRB"));
        costSubjectList.add(new CostSubject(project, "DESIGN"));
        costSubjectList.add(new CostSubject(project, "EVT"));
        costSubjectList.add(new CostSubject(project, "DVT"));
        costSubjectList.add(new CostSubject(project, "PVT"));
        costSubjectList.add(new CostSubject(project, "MP"));
        for(CostSubject costSubject : costSubjectList) {
            // 1. NPRB: 预防成本、内损成本
            String stage = costSubject.getStage();
            if(stage.equals("NPRB")) {
                costSubject.setIdentityCost(-1D);
                costSubject.setOutLossCost(-1D);
            }
            // 2. DESIGN: 预防成本、鉴定成本、内损成本
            if(stage.equals("DESIGN")) {
                costSubject.setOutLossCost(-1D);
            }
            // 3. EVT/DVT/PVT: 预防成本、鉴定成本、内损成本、外损成本
            if(stage.equals("EVT") || stage.equals("DVT") || stage.equals("PVT") ) {}
            // 4. MP: 内损、外损
            if(stage.equals("MP")) {
                costSubject.setPreventionCost(-1D);
                costSubject.setIdentityCost(-1D);
            }
        }
        repository.saveAll(costSubjectList);
    }

    @Override
    public void computeCostSubject(String project) {
        log.info("计算 CostSubject >>" + project);
        List<CostSubject> costSubjectList = getCostSubjects(project);
        for(CostSubject costSubject : costSubjectList) {
            String stage = costSubject.getStage();
            // 1. NPRB: 预防成本、内损成本
            if(stage.equals("NPRB")) {
                computeCostSubjectForNPRB(costSubject);
            }
            // 2. DESIGN: 预防成本、鉴定成本、内损成本
            if(stage.equals("DESIGN")) {
                computeCostSubjectForDesign(costSubject);
            }
            // 3. EVT/DVT/PVT: 预防成本、鉴定成本、内损成本、外损成本
            if(stage.equals("EVT") || stage.equals("DVT") || stage.equals("PVT")) {
                computeCostSubjectForEVT_DVT_PVT(costSubject);
            }
            // 4. MP: 内损、外损
            if(stage.equals("MP")) {
                computeCostSubjectForMP(costSubject);
            }
        }

        repository.saveAll(costSubjectList);
    }

    /**
     * 计算NPRB阶段的预防/内损
     * 1.预防：人员工资 + 差旅费
     * 2.内损：人员工资 + 差旅费
     * @param costSubject CostSubject
     */
    private void computeCostSubjectForNPRB(CostSubject costSubject) {
        SalaryCost salaryCost = salaryCostService.getSalaryCost(costSubject.getProject(), costSubject.getStage(), null);
        TravelCost travelCost = travelCostService.getTravelCost(costSubject.getProject(), costSubject.getStage(), null);

        // 预防
        Double preventionCost = DoubleUtil.sum(salaryCost == null ? null : salaryCost.getPreventionAmount(),
                travelCost == null ? null : travelCost.getPreventionAmount());
        // 内损
        Double inLossCost = DoubleUtil.sum(salaryCost == null ? null : salaryCost.getInLossAmount(),
                travelCost == null ? null : travelCost.getInLossAmount());

        costSubject.setPreventionCost(preventionCost);
        costSubject.setInLossCost(inLossCost);
    }

    /**
     * 计算Design阶段的预防/内损/鉴定
     * 1.预防：人员工资 + 治工具
     * 2.内损：人员工资 + 系统维护折旧费用
     * 3.鉴定：系统维护折旧费用
     * @param costSubject CostSubject
     */
    private void computeCostSubjectForDesign(CostSubject costSubject) {
        SalaryCost salaryCost = salaryCostService.getSalaryCost(costSubject.getProject(), costSubject.getStage(), null);
        JigCost jigCost = jigCostService.getJigCost(costSubject.getProject(), costSubject.getStage(), null);
        SystemMaintenanceCost systemMaintenanceCost = systemMaintenanceCostService
                .getSystemMaintenanceCost(costSubject.getProject(), costSubject.getStage(), null);

        // 预防
        Double preventionCost = DoubleUtil.sum(salaryCost == null ? null : salaryCost.getPreventionAmount(),
                jigCost == null ? null : jigCost.getAmount());
        // 鉴定
        Double identityCost = systemMaintenanceCost == null ? null : systemMaintenanceCost.getIdentityAmount();
        // 内损
        Double inLossCost = DoubleUtil.sum(salaryCost == null ? null : salaryCost.getInLossAmount(),
                systemMaintenanceCost == null ? null : systemMaintenanceCost.getInLossAmount());

        costSubject.setPreventionCost(preventionCost);
        costSubject.setInLossCost(inLossCost);
        costSubject.setIdentityCost(identityCost);
    }

    /**
     * 计算EVT/DVT/PVT阶段的预防/鉴定/内损/外损
     * 1.预防：直接材料 + 治工具 + 生产费用
     * 2.鉴定：验证
     * 3.内损：直接材料 + 治工具 + 生产费用 + 验证 + 重工/报废
     * 4.外损：OBA + RMA + 赔偿
     * @param costSubject CostSubject
     */
    private void computeCostSubjectForEVT_DVT_PVT(CostSubject costSubject) {
        String project = costSubject.getProject();
        String stage = costSubject.getStage();
        List<DirectMaterialCost> directMaterialCostList = directMaterialCostService.getDirectMaterialCosts(project, stage);
        List<JigCost> jigCostList = jigCostService.getJigCosts(project, stage);
        List<ProductionCost> productionCostList = productionCostService.getProductionCosts(project, stage);
        List<VerificationCost> verificationCostList = verificationCostService.getVerificationCosts(project, stage);
        List<ReworkScrapCost> reworkScrapCostList = reworkScrapCostService.getReworkScrapCosts(project, stage);
        List<ObaCost> obaCostList = obaCostService.getObaCosts(project, stage);
        List<RmaCost> rmaCostList = rmaCostService.getRmaCosts(project, stage);
        List<CompensationCost> compensationCostList = compensationCostSerivce.getCompensationCosts(project, stage);

        Double preventionCost = null;
        Double identityCost = null;
        Double inLossCost = null;
        Double outLossCost = null;

        // 直接材料：阶段1为预防，其他为内损
        if(directMaterialCostList != null) {
            for(DirectMaterialCost directMaterialCost : directMaterialCostList) {
                if(1 == directMaterialCost.getTime()) {
                    preventionCost = DoubleUtil.sum(preventionCost, directMaterialCost.getAmount());
                } else {
                    if(directMaterialCost.getAmount()!=null && directMaterialCost.getAmount()>0) {
                        inLossCost = DoubleUtil.sum(inLossCost, directMaterialCost.getAmount());
                    }
                }
            }
        }

        // 治工具：阶段1为预防，其他为内损
        if(jigCostList != null) {
            for(JigCost jigCost : jigCostList) {
                if(1 == jigCost.getTime()) {
                    preventionCost = DoubleUtil.sum(preventionCost, jigCost.getAmount());
                } else {
                    inLossCost = DoubleUtil.sum(inLossCost, jigCost.getAmount());
                }
            }
        }

        // 生产费用：阶段1为预防，其他为内损
        if(productionCostList != null) {
            for(ProductionCost productionCost : productionCostList) {
                if (1 == productionCost.getTime()) {
                    preventionCost = DoubleUtil.sum(preventionCost, productionCost.getAmount());
                } else {
                    inLossCost = DoubleUtil.sum(inLossCost, productionCost.getAmount());
                }
            }
        }

        // 验证费用：EVT/DVT阶段1为鉴定，其他为内损
        if(verificationCostList != null) {
            for(VerificationCost verificationCost : verificationCostList) {
                if (1 == verificationCost.getTime() &&
                        (verificationCost.getStage().equals("EVT") || verificationCost.getStage().equals("DVT"))
                ) {
                    identityCost = DoubleUtil.sum(identityCost, verificationCost.getAmount());
                } else {
                    inLossCost = DoubleUtil.sum(inLossCost, verificationCost.getAmount());
                }
            }
        }

        // 重工/报废：内损
        if(reworkScrapCostList != null) {
            for(ReworkScrapCost reworkScrapCost : reworkScrapCostList) {
                inLossCost = DoubleUtil.sum(inLossCost, reworkScrapCost.getAmount());
            }
        }

        // OBA：外损
        if(obaCostList != null) {
            for(ObaCost obaCost : obaCostList) {
                outLossCost = DoubleUtil.sum(outLossCost, obaCost.getAmount());
            }
        }

        // RMA：外损
        if(rmaCostList != null) {
            for(RmaCost rmaCost : rmaCostList) {
                outLossCost = DoubleUtil.sum(outLossCost, rmaCost.getAmount());
            }
        }

        // 赔偿：外损
        if(compensationCostList != null) {
            for(CompensationCost compensationCost : compensationCostList) {
                outLossCost = DoubleUtil.sum(outLossCost, compensationCost.getAmount());
            }
        }

        costSubject.setPreventionCost(preventionCost);
        costSubject.setIdentityCost(identityCost);
        costSubject.setInLossCost(inLossCost);
        costSubject.setOutLossCost(outLossCost);
    }

    /**
     * 计算MP阶段的内损/外损
     * 3.内损：重工/报废
     * 4.外损：OBA + RMA + 赔偿
     * @param costSubject CostSubject
     */
    private void computeCostSubjectForMP(CostSubject costSubject) {
        String project = costSubject.getProject();
        String stage = costSubject.getStage();

        List<ReworkScrapCost> reworkScrapCostList = reworkScrapCostService.getReworkScrapCosts(project, stage);
        List<CompensationCost> compensationCostList = compensationCostSerivce.getCompensationCosts(project, stage);
        List<ObaCost> obaCostList = obaCostService.getObaCosts(project, stage);
        List<RmaCost> rmaCostList = rmaCostService.getRmaCosts(project, stage);

        Double inLossCost = null;
        Double outLossCost = null;

        // 重工/报废：内损
        if(reworkScrapCostList != null) {
            for(ReworkScrapCost reworkScrapCost : reworkScrapCostList) {
                inLossCost = DoubleUtil.sum(inLossCost, reworkScrapCost.getAmount());
            }
        }

        // OBA：外损
        if(obaCostList != null) {
            for(ObaCost obaCost : obaCostList) {
                outLossCost = DoubleUtil.sum(outLossCost, obaCost.getAmount());
            }
        }


        // RMA：外损
        if(rmaCostList != null) {
            for(RmaCost rmaCost : rmaCostList) {
                outLossCost = DoubleUtil.sum(outLossCost, rmaCost.getAmount());
            }
        }

        // 赔偿：外损
        if(compensationCostList != null) {
            for(CompensationCost compensationCost : compensationCostList) {
                outLossCost = DoubleUtil.sum(outLossCost, compensationCost.getAmount());
            }
        }

        costSubject.setInLossCost(inLossCost);
        costSubject.setOutLossCost(outLossCost);
    }

    @Override
    public List<Map> getCostSubjectsConvertMap(String project) {
        List<CostSubject> costSubjectList = repository.findByProjectOrderById(project);
        Map<String, Map<String, Object>> map = new HashMap<>();
        map.put("预防成本", new HashMap<>());
        map.get("预防成本").put("costType", "预防成本");
        map.put("鉴定成本", new HashMap<>());
        map.get("鉴定成本").put("costType", "鉴定成本");
        map.put("内损成本", new HashMap<>());
        map.get("内损成本").put("costType", "内损成本");
        map.put("外损成本", new HashMap<>());
        map.get("外损成本").put("costType", "外损成本");
        for(CostSubject costSubject : costSubjectList) {
            map.get("预防成本").put(costSubject.getStage(), costSubject.getPreventionCost());
            map.get("鉴定成本").put(costSubject.getStage(), costSubject.getIdentityCost());
            map.get("内损成本").put(costSubject.getStage(), costSubject.getInLossCost());
            map.get("外损成本").put(costSubject.getStage(), costSubject.getOutLossCost());
        }

        List<Map> mapList = new ArrayList<>();
        mapList.add(map.get("预防成本"));
        mapList.add(map.get("鉴定成本"));
        mapList.add(map.get("内损成本"));
        mapList.add(map.get("外损成本"));
        return mapList;
    }
}
