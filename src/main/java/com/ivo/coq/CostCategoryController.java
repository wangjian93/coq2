package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.costCategory.compensation.service.CompensationCostSerivce;
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
import com.ivo.coq.costCategory.salary.service.SalaryCostService;
import com.ivo.coq.costCategory.systemMaintenance.serivce.SystemMaintenanceCostService;
import com.ivo.coq.costCategory.travel.service.TravelCostDetailService;
import com.ivo.coq.costCategory.travel.service.TravelCostService;
import com.ivo.coq.costCategory.verification.service.VerificationCostBmDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationCostPlantDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq/category")
public class CostCategoryController {

    private DirectMaterialCostService directMaterialCostService;

    private MaterialCostDetailService materialCostDetailService;

    private OutsourcingThinningCostDetailService outsourcingThinningCostDetailService;

    private ShipmentCostDetailService shipmentCostDetailService;

    private JigCostService jigCostService;
    private JigCostDetailService jigCostDetailService;

    private VerificationCostService verificationCostService;
    private VerificationCostBmDetailService verificationCostBmDetailService;
    private VerificationCostPlantDetailService verificationCostPlantDetailService;

    private SalaryCostService salaryCostService;

    private TravelCostService travelCostService;
    private TravelCostDetailService travelCostDetailService;

    private SystemMaintenanceCostService systemMaintenanceCostService;

    private ObaCostService obaCostService;
    private ObaCostDetailService obaCostDetailService;

    private CompensationCostSerivce compensationCostSerivce;

    private ProductionCostService productionCostService;
    private ProductionCostDetailService productionCostDetailService;

    private ReworkScrapCostService reworkScrapCostService;
    private ReworkScrapCostDetailService reworkScrapCostDetailService;

    @Autowired
    public CostCategoryController(DirectMaterialCostService directMaterialCostService, MaterialCostDetailService materialCostDetailService,
                                  OutsourcingThinningCostDetailService outsourcingThinningCostDetailService,
                                  JigCostService jigCostService, JigCostDetailService jigCostDetailService,
                                  VerificationCostService verificationCostService,
                                  VerificationCostBmDetailService verificationCostBmDetailService,
                                  VerificationCostPlantDetailService verificationCostPlantDetailService,
                                  SalaryCostService salaryCostService,
                                  TravelCostService travelCostService,
                                  TravelCostDetailService travelCostDetailService,
                                  SystemMaintenanceCostService systemMaintenanceCostService,
                                   ObaCostService obaCostService, ObaCostDetailService obaCostDetailService,
                                  CompensationCostSerivce compensationCostSerivce,
                                  ProductionCostService productionCostService,
                                  ProductionCostDetailService productionCostDetailService,
                                  ReworkScrapCostService reworkScrapCostService,
                                  ReworkScrapCostDetailService reworkScrapCostDetailService,
                                  ShipmentCostDetailService shipmentCostDetailService
    ) {
        this.directMaterialCostService = directMaterialCostService;
        this.materialCostDetailService = materialCostDetailService;
        this.outsourcingThinningCostDetailService = outsourcingThinningCostDetailService;
        this.jigCostService = jigCostService;
        this.jigCostDetailService = jigCostDetailService;
        this.verificationCostService = verificationCostService;
        this.verificationCostBmDetailService = verificationCostBmDetailService;
        this.verificationCostPlantDetailService = verificationCostPlantDetailService;
        this.salaryCostService = salaryCostService;
        this.travelCostService = travelCostService;
        this.travelCostDetailService = travelCostDetailService;
        this.systemMaintenanceCostService = systemMaintenanceCostService;
        this.obaCostService = obaCostService;
        this.obaCostDetailService = obaCostDetailService;
        this.compensationCostSerivce = compensationCostSerivce;
        this.productionCostService = productionCostService;
        this.productionCostDetailService = productionCostDetailService;
        this.reworkScrapCostService = reworkScrapCostService;
        this.reworkScrapCostDetailService = reworkScrapCostDetailService;
        this.shipmentCostDetailService = shipmentCostDetailService;
    }

    /**
     * 获取机种直接材料费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/directMaterialCost/{project}")
    public PageResult getDirectMaterialCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(directMaterialCostService.getDirectMaterialCosts(project));
    }

    /**
     * 获取机种直接材料费用-材料费用部分
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/materialCostDetail/{project}")
    public PageResult getMaterialCostDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(materialCostDetailService.getMaterialCostDetails(project));
    }

    /**
     * 获取机种直接材料费用-外包薄化费用部分
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/shipmentCostDetail/{project}")
    public PageResult shipmentCostDetailService(@PathVariable("project") String project) {
        return ResultUtil.successPage(shipmentCostDetailService.getShipmentCostDetail(project));
    }

    /**
     * 获取机种直接材料费用-出货费用部分
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/outsourcingThinningCostDetail/{project}")
    public PageResult getOutsourcingThinningCostDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(outsourcingThinningCostDetailService.getOutsourcingThinningCostDetails(project));
    }

    /**
     * 获取机种的治工具费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/jigCost/{project}")
    public PageResult getJigCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(jigCostService.getJigCosts(project));
    }

    /**
     * 获取机种治工具费用的详细
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/jigCostDetail/{project}")
    public PageResult getJigCostDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(jigCostDetailService.getJigCostDetail(project));
    }

    /**
     * 获取机种验证费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/verificationCost/{project}")
    public PageResult getVerificationCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(verificationCostService.getVerificationCosts(project));
    }

    /**
     * 获取机种验证费用-BM部分
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/verificationCostBmDetail/{project}")
    public PageResult getVerificationCostBmDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(verificationCostBmDetailService.getVerificationCostBmDetail(project));
    }

    /**
     * 获取机种验证费用-厂内验证部分
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/verificationCostPlantDetail/{project}")
    public PageResult getVerificationCostPlantDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(verificationCostPlantDetailService.getVerificationCostPlantDetail(project));
    }

    /**
     * 获取机种人员薪资费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/salaryCost/{project}")
    public PageResult getSalaryCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(salaryCostService.getSalaryCosts(project));
    }

    /**
     * 获取机种差旅费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/travelCost/{project}")
    public PageResult getTravelCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(travelCostService.getTravelCosts(project));
    }

    /**
     * 获取机种差旅费用详情
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/travelCostDetail/{project}")
    public PageResult getTravelCostDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(travelCostDetailService.getTravelCostDetail(project));
    }

    /**
     * 获取机种系统维护折旧费
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/systemMaintenanceCost/{project}")
    public PageResult getSystemMaintenanceCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(systemMaintenanceCostService.getSystemMaintenanceCosts(project));
    }


    /**
     * 获取机种OBA费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/obaCost/{project}")
    public PageResult getObaCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(obaCostService.getObaCosts(project));
    }

    /**
     * 获取机种OBA费用详情
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/obaCostDetail/{project}")
    public PageResult getObaCostDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(obaCostDetailService.getObaCostDetail(project));
    }

    /**
     * 获取机种赔偿费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/compensationCost/{project}")
    public PageResult getCompensationCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(compensationCostSerivce.getCompensationCosts(project));
    }

    /**
     * 获取机种生产费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/productionCost/{project}")
    public PageResult getProductionCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(productionCostService.getProductionCosts(project));
    }

    /**
     * 获取机种生产费用详细
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/productionCostDetail/{project}")
    public PageResult getProductionCostDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(productionCostDetailService.getProductionCostDetail(project));
    }

    @GetMapping("/reworkScrapCost/{project}")
    public PageResult getReworkScrapCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(reworkScrapCostService.getReworkScraps(project));
    }

    @GetMapping("/reworkScrapCostDetail/{project}")
    public PageResult getReworkScrapCostDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(reworkScrapCostDetailService.getReworkScrapCostDetail(project));
    }

}
