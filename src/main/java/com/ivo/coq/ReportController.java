package com.ivo.coq;

import com.ivo.common.enums.PlantEnum;
import com.ivo.common.result.PageResult;
import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.cost.entity.CostSubject;
import com.ivo.coq.cost.service.CostService;
import com.ivo.coq.cost.service.CostSubjectService;
import com.ivo.coq.report.entity.InLossAmount;
import com.ivo.coq.report.service.InLossAmountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    public ReportController(CostSubjectService costSubjectService,
                            CostService costService,
                            InLossAmountService inLossAmountService) {
        this.costSubjectService = costSubjectService;
        this.costService = costService;
        this.inLossAmountService = inLossAmountService;
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
        FAB_DATE += "-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.equalsAnyIgnoreCase(FAB_ID, PlantEnum.Lcm1.getPlant(), PlantEnum.Lcm2.getPlant())) {
            return  ResultUtil.success(inLossAmountService.getInLossAmountDetailLcm(FAB_ID, sdf.parse(FAB_DATE)));
        } else {
            return  ResultUtil.success(inLossAmountService.getInLossAmountDetailArrayCell(FAB_ID, sdf.parse(FAB_DATE)));
        }
    }
}
