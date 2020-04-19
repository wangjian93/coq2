package com.ivo.rest.bm;

import com.ivo.rest.bm.entity.BmModel;

import java.util.Date;
import java.util.List;

/**
 * BMs数据接口
 * @author wj
 * @version 1.0
 */
public interface BmService {

    /**
     * 根据PR获取机种的外包薄化费用
     * @param pr 采购请求单
     * @return List
     */
    List<BmModel> getBmOutsourcingThinning(String pr);

    /**
     * 获取机种的BM治工具费用
     * @param project 机种
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    List<BmModel> getBmJig(String project, Date fromDate, Date toDate);

    /**
     * 获取机种的BM验证费用
     * @param project 机种
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    List<BmModel> getBmVerification(String project, Date fromDate, Date toDate);
}
