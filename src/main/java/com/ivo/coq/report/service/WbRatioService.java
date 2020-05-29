package com.ivo.coq.report.service;

import com.ivo.coq.report.entity.WbRatio;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface WbRatioService {

    /**
     * 同步外部失败成本率数据
     */
    void syncWbRatio();

    /**
     * 获取外部失败成本率
     * @param fromDate 日期
     * @param toDate 日期
     * @param fabId 厂别
     * @return
     */
    List<WbRatio> getWbRatio(Date fromDate, Date toDate, String fabId);
}
