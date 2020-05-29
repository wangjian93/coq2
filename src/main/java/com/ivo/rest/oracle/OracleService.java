package com.ivo.rest.oracle;

import com.ivo.coq.report.entity.*;
import com.ivo.rest.oracle.entity.OracleReworkScrapArray;
import com.ivo.rest.oracle.entity.OracleReworkScrapCell;
import com.ivo.rest.oracle.entity.OracleReworkScrapLcm;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface OracleService {

    /**
     * 获取单片材料费用 （展开BOM）
     * @param product 机种
     * @return Double
     */
    Double getProductPerAmount(String product);

    /**
     * 获取工单费用
     * @param wo 工单
     * @return Double
     */
    Double getWoAmount(String wo);

    /**
     * 获取料号的单价
     * @param material 料号
     * @return Double
     */
    Double getMaterialPrice(String material);

    /**
     * 获取Array的重工报废数据
     * @param fromDate 投产时间
     * @param toDate 产出时间
     * @param prodId 产品
     * @return List<OracleReworkScrapArray>
     */
    List<OracleReworkScrapArray> getOracleReworkScrapArray(Date fromDate, Date toDate, String prodId);


    /**
     * 获取Cell的重工报废数据
     * @param fromDate 投产时间
     * @param toDate 产出时间
     * @param prodId 产品
     * @param tft TFT
     * @param cf CF
     * @return List<OracleReworkScrapCell>
     */
    List<OracleReworkScrapCell> getOracleReworkScrapCell(Date fromDate,Date toDate, String prodId, String tft, String cf);


    /**
     * 获取LCM的重工报废数据
     * @param fromDate 投产时间
     * @param toDate 产出时间
     * @param wo 工单
     * @return List<OracleReworkScrapLcm>
     */
    List<OracleReworkScrapLcm> getOracleReworkScrapLcm(Date fromDate, Date toDate, String wo);

    /**
     * 获取ARRAY/CELL的内损成本
     * @return List<InLossAmount>
     */
    List<InLossAmount> getInLossAmountArrayCell();

    /**
     * 获取LCM的内损成本
     * @return List<InLossAmount>
     */
    List<InLossAmount> getInLossAmountArrayLcm();

    /**
     * 获取Array/cell/lcm的总制造成本
     * @return List<TotalAmount>
     */
    List<TotalAmount> getTotalAmount();

    /**
     * 获取ARRAY/CELL的内损成本明细
     * @return List<InLossAmountDetailArrayCell>
     */
    List<InLossAmountDetailArrayCell> getInLossAmountDetailArrayCell();

    /**
     * 获取LCM的内损成本明细
     * @return List<InLossAmountDetailLcm>
     */
    List<InLossAmountDetailLcm> getInLossAmountDetailLcm();

    /**
     * 获取工单的Shipping数量
     * @return Double
     */
    Double getWoShippingQty(String wo);

    /**
     * 获取外部失败成本率数据
     * @return
     */
    List<WbRatio> getWbRatio();
}
