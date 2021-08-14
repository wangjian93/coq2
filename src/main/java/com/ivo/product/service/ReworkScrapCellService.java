package com.ivo.product.service;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCellService {

    /**
     * 同步Ary重工报废数据
     * @param month 月份
     */
    void syncCellReworkScrap(String month);

    /**
     * 计算Ary的重工报废费用
     * @param month 月份
     */
    void computeCellReworkScrapAmount(String month);

    void computeReworkScrapMonth(String month);
}
