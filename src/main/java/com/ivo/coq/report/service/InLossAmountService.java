package com.ivo.coq.report.service;

import com.ivo.coq.report.entity.InLossAmount;
import com.ivo.coq.report.entity.InLossAmountDetailArrayCell;
import com.ivo.coq.report.entity.InLossAmountDetailLcm;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface InLossAmountService {

    /**
     * 获取各厂的内损费用
     * @param FAB_ID 厂别
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return List<InLossAmount>
     */
    List<InLossAmount> getInLossAmount(String FAB_ID, Date fromDate, Date toDate);

    /**
     * 获取各厂的内损费用的比率
     * @param FAB_ID 厂别
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    List<Map> getInLossAmountRatio(String FAB_ID, Date fromDate, Date toDate);

    /**
     * 获取Array/cell厂内损费用明细
     * @param FAB_ID 厂别
     * @param FAB_DATE 日期表月份
     * @return List<InLossAmountDetailArrayCell>
     */
    List<InLossAmountDetailArrayCell> getInLossAmountDetailArrayCell(String FAB_ID, Date FAB_DATE);

    /**
     * 获取LCM厂内损费用明细
     * @param FAB_ID 厂别LCM1/LCM2
     * @param FAB_DATE 日期表月份
     * @return List<InLossAmountDetailLcm>
     */
    List<InLossAmountDetailLcm> getInLossAmountDetailLcm(String FAB_ID, Date FAB_DATE);

    /**
     * 同步各厂的内损成本
     */
    void syncInLossAmount();

    /**
     * 同步Array/CELL的内损成本明细
     */
    void syncInLossAmountDetailArrayCell();

    /**
     * 同步LCM的内损成本明细
     */
    void syncInLossAmountDetailLcm();
}
