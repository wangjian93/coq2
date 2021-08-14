package com.ivo.coq;

import com.ivo.common.enums.PlantEnum;
import com.ivo.common.enums.StageEnum;
import com.ivo.common.result.PageResult;
import com.ivo.common.result.Result;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.cost.entity.CostStage;
import com.ivo.coq.cost.entity.CostSubject;
import com.ivo.coq.cost.service.CostService;
import com.ivo.coq.cost.service.CostStageService;
import com.ivo.coq.cost.service.CostSubjectService;
import com.ivo.coq.report.entity.InLossAmount;
import com.ivo.coq.report.service.InLossAmountService;
import com.ivo.coq.report.service.WbRatioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq/report")
public class ReportController {

    private CostService costService;
    private CostSubjectService costSubjectService;

    private InLossAmountService inLossAmountService;

    private CostStageService costStageService;

    private WbRatioService wbRatioService;

    @Autowired
    public ReportController(CostSubjectService costSubjectService,
                            CostService costService,
                            InLossAmountService inLossAmountService,
                            CostStageService costStageService,
                            WbRatioService wbRatioService) {
        this.costSubjectService = costSubjectService;
        this.costService = costService;
        this.inLossAmountService = inLossAmountService;
        this.costStageService = costStageService;
        this.wbRatioService = wbRatioService;
    }

    /**
     * 获取机种成本二级科目
     * VIP - COQ COST
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/costSubject/{project}")
    public PageResult getCostSubjectsConvert(@PathVariable("project") String project) {
        return ResultUtil.successPage(costSubjectService.getCostSubjectsConvertMap(project));
    }

    /**
     * 获取多个机种的成本二级科目
     * 新产品 - TOTAL COST
     * @param projects 机种数组
     * @return PageResult
     */
    @PostMapping("/costSubjects")
    public PageResult getCostSubjects(@RequestParam(value = "projects[]") String[] projects) {
        List<CostSubject> list = new ArrayList<>();
        if(projects != null && projects.length > 0) {
            for(String project : projects) {
                list.addAll(costSubjectService.getCostSubjects(project));
            }
        }
        return ResultUtil.successPage(list);
    }

    /**
     * 获取时间段内所有开案机种的成本
     * 新产品 - TOTAL RATIO报表
     * @param fromDate 开始
     * @param toDate 结束
     * @return PageResult
     */
    @PostMapping("/cost")
    public PageResult getCost(@DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return ResultUtil.successPage(costService.getCosts());
    }


    /**
     * 获取各厂的内损费用及比率
     * @param FAB_ID 厂别
     * @param fromMonth 开始
     * @param toMonth 结束
     * @return Result
     */
    @PostMapping("/inLossAmount")
    public Result getInLossAmount(String FAB_ID, String fromMonth, String toMonth) throws ParseException {
        fromMonth += "-01";
        toMonth += "-1";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  ResultUtil.success(inLossAmountService.getInLossAmountRatio(FAB_ID, sdf.parse(fromMonth), sdf.parse(toMonth)));
    }

    /**
     * 获取ARRAY/CELL/LCM内损费用明细
     * @param FAB_ID 厂别
     * @param FAB_DATE 日期月份
     * @return success
     */
    @PostMapping("/inLossAmountDetail")
    public Result getInLossAmountDetail(String FAB_ID, String FAB_DATE) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        if(StringUtils.equalsAnyIgnoreCase(FAB_ID, PlantEnum.Lcm1.getPlant(), PlantEnum.Lcm2.getPlant())) {
            return  ResultUtil.success(inLossAmountService.getInLossAmountDetailLcm(FAB_ID, sdf.parse(FAB_DATE)));
        } else {
            return  ResultUtil.success(inLossAmountService.getInLossAmountDetailArrayCell(FAB_ID, sdf.parse(FAB_DATE)));
        }
    }

    /**
     * 获取PolitRun Ratio
     * @param projects 机种
     * @return
     */
    @PostMapping("/getPolitRunRatio")
    public Result getPolitRunRatio(@RequestParam(value = "projects[]") String[] projects) {
        List<Map> mapList = new ArrayList<>();
        for(String project : projects) {
            //["NPRB", "DESIGN", 'EVT', 'DVT', 'PVT', 'MP']
            double NPRB_T = 0;
            double DESIGN_T = 0;
            double EVT_T = 0;
            double DVT_T = 0;
            double PVT_T = 0;
            double MP_T = 0;
            List<CostSubject> costSubjectList =  costSubjectService.getCostSubjects(project);
            for(CostSubject costSubject : costSubjectList) {
                Double d = DoubleUtil.sum(costSubject.getOutLossCost(), costSubject.getInLossCost(), costSubject.getIdentityCost(), costSubject.getPreventionCost());
                if(d == null || d<0) d=0D;
                if(StringUtils.equalsIgnoreCase(costSubject.getStage(), StageEnum.NPRB.getStage())) {
                    NPRB_T = d;
                }
                if(StringUtils.equalsIgnoreCase(costSubject.getStage(), StageEnum.Design.getStage())) {
                    DESIGN_T = d;
                }
                if(StringUtils.equalsIgnoreCase(costSubject.getStage(), StageEnum.EVT.getStage())) {
                    EVT_T = d;
                }
                if(StringUtils.equalsIgnoreCase(costSubject.getStage(), StageEnum.DVT.getStage())) {
                    DVT_T = d;
                }
                if(StringUtils.equalsIgnoreCase(costSubject.getStage(), StageEnum.PVT.getStage())) {
                    PVT_T = d;
                }
                if(StringUtils.equalsIgnoreCase(costSubject.getStage(), StageEnum.MP.getStage())) {
                    MP_T = d;
                }
            }
            double NPRB_s = 0;
            double DESIGN_s = 0;
            double EVT_s = 0;
            double DVT_s = 0;
            double PVT_s = 0;
            double MP_s = 0;
            List<CostStage> costStageList = costStageService.getCostStages(project);
            for(CostStage costStage : costStageList) {
                if(costStage.getTime() == null || costStage.getTime() == 1) continue;
                Double d = DoubleUtil.sum(costStage.getCompensationCost(), costStage.getDirectMaterialCost(), costStage.getJigCost(),
                        costStage.getObaCost(), costStage.getProductionCost(), costStage.getReworkScrapCost(), costStage.getRmaCost(),
                        costStage.getSalaryCost(), costStage.getSystemMaintenanceCost(), costStage.getTravelCost(), costStage.getVerificationCost());
                if(d == null || d<0) d=0D;
                if(StringUtils.equalsIgnoreCase(costStage.getStage(), StageEnum.NPRB.getStage())) {
                    NPRB_s = DoubleUtil.sum(NPRB_s, d);
                }
                if(StringUtils.equalsIgnoreCase(costStage.getStage(), StageEnum.Design.getStage())) {
                    DESIGN_s = DoubleUtil.sum(DESIGN_s, d);
                }
                if(StringUtils.equalsIgnoreCase(costStage.getStage(), StageEnum.EVT.getStage())) {
                    EVT_s = DoubleUtil.sum(EVT_s, d);
                }
                if(StringUtils.equalsIgnoreCase(costStage.getStage(), StageEnum.DVT.getStage())) {
                    DVT_s = DoubleUtil.sum(DVT_s, d);
                }
                if(StringUtils.equalsIgnoreCase(costStage.getStage(), StageEnum.PVT.getStage())) {
                    PVT_s = DoubleUtil.sum(PVT_s, d);
                }
                if(StringUtils.equalsIgnoreCase(costStage.getStage(), StageEnum.MP.getStage())) {
                    MP_s = DoubleUtil.sum(MP_s, d);
                }
            }
            Map<String, Object> subMap = new HashMap<>();
            subMap.put("NPRB", NPRB_s ==0 || NPRB_T == 0 ? 0 : DoubleUtil.divide(NPRB_s, NPRB_T));
            subMap.put("DESIGN", DESIGN_s ==0 || DESIGN_T == 0 ? 0 : DoubleUtil.divide(DESIGN_s, DESIGN_T));
            subMap.put("EVT", EVT_s ==0 || EVT_T == 0 ? 0 : DoubleUtil.divide(EVT_s, EVT_T));
            subMap.put("DVT", DVT_s ==0 || DVT_T == 0 ? 0 : DoubleUtil.divide(DVT_s, DVT_T));
            subMap.put("PVT", PVT_s ==0 || PVT_T == 0 ? 0 : DoubleUtil.divide(PVT_s, PVT_T));
            subMap.put("MP", MP_s ==0 || MP_T == 0 ? 0 : DoubleUtil.divide(MP_s, MP_T));
            subMap.put("project", project);
            mapList.add(subMap);
        }
        return ResultUtil.success(mapList);
    }



    /**
     * 获取外部失败成本率
     * @param FAB_ID 厂别
     * @param fromMonth 开始月份
     * @param toMonth 结束月份
     * @return
     */
    @RequestMapping("/getWbRatio")
    public Result getWbRatio(String FAB_ID, String fromMonth, String toMonth) throws ParseException {
        fromMonth += "-01";
        toMonth += "-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  ResultUtil.success(wbRatioService.getWbRatio(sdf.parse(fromMonth), sdf.parse(toMonth), FAB_ID));
    }
}
