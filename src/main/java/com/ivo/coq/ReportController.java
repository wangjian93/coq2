package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.cost.entity.CostSubject;
import com.ivo.coq.cost.service.CostSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq/report")
public class ReportController {

    private CostSubjectService costSubjectService;

    @Autowired
    public ReportController(CostSubjectService costSubjectService) {
        this.costSubjectService = costSubjectService;
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
}
