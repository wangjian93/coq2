package com.ivo.np.controller;

import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.np.service.FailureCostRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * KPI新产品开发失败成本率
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/np/failureCostRate")
public class FailureCostRateController {

    @Autowired
    private FailureCostRateService failureCostRateService;

    /**
     * 获取时间区间内的新产品开发失败成本率
     * @param fromMonth 开始月份
     * @param toMonth 结束月份
     * @return  Result
     **/
    @GetMapping("/getFailureCostRate")
    public Result getFailureCostRate(String fromMonth, String toMonth) {
        return ResultUtil.success(failureCostRateService.getFailureCostRate(fromMonth, toMonth));
    }
}
