package com.ivo.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface RestService {

    /**
     * 出差报支单接口获取人员的差旅费信息
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @param employee 员工工号
     * @return
     */
    List<Map<String, String>> getTravelDetailsFromBe(Date fromDate, Date toDate, String employee);
}
