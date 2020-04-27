package com.ivo.coq.report.service;

import com.ivo.coq.report.entity.InLossAmount;
import com.ivo.coq.report.entity.InLossAmountDetail;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface InLossAmountService {

    /**
     *
     * @param FAB_ID 厂别
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return List<InLossAmount>
     */
    List<InLossAmount> getInLossAmount(String FAB_ID, Date fromDate, Date toDate);


    /**
     *
     * @param FAB_ID 厂别
     * @param FAB_DATE 日期
     * @return
     */
    List<InLossAmountDetail> getInLossAmountDetail(String FAB_ID, Date FAB_DATE);
}
