package com.ivo.np.service;

import com.ivo.np.entity.FailureCostRate;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface FailureCostRateService {

    /**
     * 获取时间区间内的新产品开发失败成本率
     * @param fromMonth 开始月份
     * @param toMonth 结束月份
     * @return  List<FailureCostRate>
     **/
     List<FailureCostRate> getFailureCostRate(String fromMonth, String toMonth);
}
