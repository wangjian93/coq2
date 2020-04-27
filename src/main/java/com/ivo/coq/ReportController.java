package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.cost.entity.CostSubject;
import com.ivo.coq.cost.service.CostService;
import com.ivo.coq.cost.service.CostSubjectService;
import com.ivo.coq.report.entity.InLossAmount;
import com.ivo.coq.report.service.InLossAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/inLossAmount")
    public PageResult getInLossAmount(@DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate, String FAB_ID) {
        return  ResultUtil.successPage(inLossAmountService.getInLossAmount(FAB_ID, fromDate, toDate));
    }

    @PostMapping("/inLossAmountDetail")
    public PageResult getInLossAmountDetail(
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date FAB_DATE, String FAB_ID) {
        return  ResultUtil.successPage(inLossAmountService.getInLossAmountDetail(FAB_ID, FAB_DATE));
    }

}
