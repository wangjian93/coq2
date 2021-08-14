package com.ivo.coq.report.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    List<Map> getWbRatio(Date fromDate, Date toDate, String fabId);

    Map getWbRatio(List<String> monthList, String fabId) throws ParseException;
}
