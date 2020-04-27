package com.ivo.rest.oracle;

import com.ivo.rest.oracle.entity.OracleReworkScrapArray;
import com.ivo.rest.oracle.entity.OracleReworkScrapCell;
import com.ivo.rest.oracle.entity.OracleReworkScrapLcm;
import org.apache.ibatis.annotations.Param;

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
     * @return
     */
    Double getProductPerAmount(String product);

    /**
     * 获取工单费用
     * @param wo 工单
     * @return
     */
    Double getWoAmount(String wo);

    /**
     * 获取料号的单价
     * @param material 料号
     * @return
     */
    Double getMaterialPrice(String material);

    /**
     * 获取Array的重工报废数据
     * @param fromDate 投产时间
     * @param toDate 产出时间
     * @param prodId 产品
     * @return
     */
    List<OracleReworkScrapArray> getOracleReworkScrapArray(Date fromDate, Date toDate, String prodId);


    /**
     * 获取Cell的重工报废数据
     * @param fromDate 投产时间
     * @param toDate 产出时间
     * @param prodId 产品
     * @param tft TFT
     * @param cf CF
     * @return
     */
    List<OracleReworkScrapCell> getOracleReworkScrapCell(Date fromDate,Date toDate, String prodId, String tft, String cf);


    /**
     * 获取LCM的重工报废数据
     * @param fromDate 投产时间
     * @param toDate 产出时间
     * @param wo 工单
     * @return
     */
    List<OracleReworkScrapLcm> getOracleReworkScrapLcm(Date fromDate, Date toDate, String wo);
}
