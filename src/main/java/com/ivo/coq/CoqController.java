package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.cost.entity.Cost;
import com.ivo.coq.cost.entity.CostStage;
import com.ivo.coq.cost.entity.CostSubject;
import com.ivo.coq.cost.service.CostService;
import com.ivo.coq.cost.service.CostStageService;
import com.ivo.coq.cost.service.CostSubjectService;
import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.entity.ProjectStage;
import com.ivo.coq.project.service.ProjectScheduleService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * COQ 数据接口
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq")
public class CoqController {

    private ProjectService projectService;

    private ProjectScheduleService projectScheduleService;

    private CostService costService;

    private CostSubjectService costSubjectService;

    private CostStageService costStageService;

    private CompensationCostSerivce compensationCostSerivce;

    private DirectMaterialCostService directMaterialCostService;

    private JigCostService jigCostService;

    private ObaCostService obaCostService;

    private ProductionCostService productionCostService;

    private ReworkScrapCostService reworkScrapCostService;

    private RmaCostService rmaCostService;

    private SalaryCostService salaryCostService;

    private SystemMaintenanceCostService systemMaintenanceCostService;

    private TravelCostService travelCostService;

    private VerificationCostService verificationCostService;

    @Autowired
    public CoqController(ProjectService projectService, ProjectScheduleService projectScheduleService,
                         CostService costService, CostSubjectService costSubjectService,
                         CostStageService costStageService, CompensationCostSerivce compensationCostSerivce,
                         DirectMaterialCostService directMaterialCostService, JigCostService jigCostService,
                         ObaCostService obaCostService, ProductionCostService productionCostService,
                         ReworkScrapCostService reworkScrapCostService, RmaCostService rmaCostService,
                         SalaryCostService salaryCostService, SystemMaintenanceCostService systemMaintenanceCostService,
                         TravelCostService travelCostService, VerificationCostService verificationCostService) {
        this.projectService = projectService;
        this.projectScheduleService = projectScheduleService;
        this.costService = costService;
        this.costSubjectService = costSubjectService;
        this.costStageService = costStageService;
        this.compensationCostSerivce = compensationCostSerivce;
        this.directMaterialCostService = directMaterialCostService;
        this.jigCostService = jigCostService;
        this.obaCostService = obaCostService;
        this.productionCostService = productionCostService;
        this.reworkScrapCostService = reworkScrapCostService;
        this.rmaCostService = rmaCostService;
        this.salaryCostService = salaryCostService;
        this.systemMaintenanceCostService = systemMaintenanceCostService;
        this.travelCostService = travelCostService;
        this.verificationCostService = verificationCostService;
    }

    /**
     * 获取机种列表
     * @return PageResult
     */
    @GetMapping("/projects")
    public PageResult getProjects() {
        List<Project> projectList = projectService.getProjects();
        return ResultUtil.successPage(projectList);
    }

    /**
     * 获取机种列表，根据类型
     * @param type 类型
     * @return PageResult
     */
    @GetMapping("/projects/{type}")
    public PageResult getProjectsByType(@PathVariable("type") String type) {
        List<Project> projectList = projectService.getProjectsByType(type);
        return ResultUtil.successPage(projectList);
    }

    /**
     * 获取机种列表，根据类型和尺寸
     * @param type 类型
     * @param size 尺寸
     * @return PageResult
     */
    @GetMapping("/projects/{type}/{size}")
    public PageResult getProjectsByTypeAndSize(@PathVariable("type") String type, @PathVariable("size") String size) {
        List<Project> projectList = projectService.getProjectsByTypeAndSize(type, size);
        return ResultUtil.successPage(projectList);
    }

    /**
     * 获取机种阶段
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/projectStages/{project}")
    public PageResult getProjectStages(@PathVariable("project") String project) {
        List<ProjectStage> projectStageList = projectService.getProjectStages(project);
        return ResultUtil.successPage(projectStageList);
    }

    /**
     * 获取机种进度
     * @param project 机种
     * @return Result
     */
    @GetMapping("/projectSchedules/{project}")
    public Result projectSchedules(@PathVariable("project") String project) {
        Map map = projectScheduleService.getSchedules(project);
        return ResultUtil.success(map);
    }

    /**
     * 获取机种成本
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/costs/{project}")
    public PageResult getCost(@PathVariable("project") String project) {
        Cost cost = costService.getCost(project);
        List<Cost> list = new ArrayList<>();
        list.add(cost);
        return ResultUtil.successPage(list);
    }

    /**
     * 获取机种成本二级科目
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/costSubjects/{project}")
    public PageResult getCostSubjects(@PathVariable("project") String project) {
        List<CostSubject> costSubjectList = costSubjectService.getCostSubjects(project);
        return ResultUtil.successPage(costSubjectList);
    }

    /**
     * 获取机种成本二级科目，数据格式改变为行专列
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/costSubjects/convert/{project}")
    public PageResult getCostSubjectsConvert(@PathVariable("project") String project) {
        List<CostSubject> costSubjectList = costSubjectService.getCostSubjects(project);
        Map<String, Map> map = new HashMap<>();
        map.put("预防成本", new HashMap());
        map.get("预防成本").put("costType", "预防成本");
        map.put("鉴定成本", new HashMap());
        map.get("鉴定成本").put("costType", "鉴定成本");
        map.put("内损成本", new HashMap());
        map.get("内损成本").put("costType", "内损成本");
        map.put("外损成本", new HashMap());
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

        return ResultUtil.successPage(mapList);
    }

    /**
     * 获取机种成本二级科目
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/costStages/{project}")
    public PageResult getCostStages(@PathVariable("project") String project) {
        List<CostStage> costStageList = costStageService.getCostStages(project);
        return ResultUtil.successPage(costStageList);
    }

    /**
     * 获取机种赔偿费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/compensationCosts/{project}")
    public PageResult getCompensationCost(@PathVariable("project") String project) {
        List<CompensationCost> compensationCostList = compensationCostSerivce.getCompensationCosts(project);
        return ResultUtil.successPage(compensationCostList);
    }

    /**
     * 获取机种直接材料费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/directMaterialCosts/{project}")
    public PageResult getDirectMaterialCost(@PathVariable("project") String project) {
        List<DirectMaterialCost> directMaterialCostList = directMaterialCostService.getDirectMaterialCosts(project);
        return ResultUtil.successPage(directMaterialCostList);
    }

    /**
     * 获取机种的治工具费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/jigCosts/{project}")
    public PageResult getJigCost(@PathVariable("project") String project) {
        List<JigCost> jigCostList = jigCostService.getJigCosts(project);
        return ResultUtil.successPage(jigCostList);
    }

    /**
     * 获取机种的OBA费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/obaCosts/{project}")
    public PageResult getObaCost(@PathVariable("project") String project) {
        List<ObaCost> obaCostList = obaCostService.getObaCosts(project);
        return ResultUtil.successPage(obaCostList);
    }

    /**
     * 获取机种的生产费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/productionCosts/{project}")
    public PageResult getProductionCost(@PathVariable("project") String project) {
        List<ProductionCost> productionCostList = productionCostService.getProductionCosts(project);
        return ResultUtil.successPage(productionCostList);
    }

    /**
     * 获取机种的重工报废费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/reworkScrapCosts/{project}")
    public PageResult getReworkScrapCost(@PathVariable("project") String project) {
        List<ReworkScrapCost> reworkScrapCostList = reworkScrapCostService.getReworkScraps(project);
        return ResultUtil.successPage(reworkScrapCostList);
    }

    /**
     * 获取机种的RMA费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/rmaCosts/{project}")
    public PageResult getRmaCost(@PathVariable("project") String project) {
        List<RmaCost> rmaCostList = rmaCostService.getRmaCosts(project);
        return ResultUtil.successPage(rmaCostList);
    }

    /**
     * 获取机种的人员工资费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/salaryCosts/{project}")
    public PageResult getSalaryCost(@PathVariable("project") String project) {
        List<SalaryCost> salaryCostList = salaryCostService.getSalaryCosts(project);
        return ResultUtil.successPage(salaryCostList);
    }

    /**
     * 获取机种的系统维护折旧费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/systemMaintenanceCosts/{project}")
    public PageResult gerSystemMaintenanceCost(@PathVariable("project") String project) {
        List<SystemMaintenanceCost> systemMaintenanceCostList = systemMaintenanceCostService.getSystemMaintenanceCosts(project);
        return ResultUtil.successPage(systemMaintenanceCostList);
    }

    /**
     * 获取机种的差旅交际费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/travelCosts/{project}")
    public PageResult getTravelCost(@PathVariable("project") String project) {
        List<TravelCost> travelCostList = travelCostService.getTravelCosts(project);
        return ResultUtil.successPage(travelCostList);
    }

    /**
     * 获取机种的验证费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/verificationCosts/{project}")
    public PageResult getVerificationCost(@PathVariable("project") String project) {
        List<VerificationCost> verificationCostList = verificationCostService.getVerificationCosts(project);
        return ResultUtil.successPage(verificationCostList);
    }
}
