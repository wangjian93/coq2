package com.ivo.rest.oracle.mapper;

import com.ivo.rest.oracle.entity.OracleReworkScrapArray;
import com.ivo.rest.oracle.entity.OracleReworkScrapCell;
import com.ivo.rest.oracle.entity.OracleReworkScrapLcm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Repository
public interface OracleMapper {
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
    List<OracleReworkScrapArray>  getOracleReworkScrapArray(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
                                                            @Param("prodId") String prodId);


    /**
     * 获取Cell的重工报废数据
     * @param fromDate 投产时间
     * @param toDate 产出时间
     * @param prodId 产品
     * @param tft TFT
     * @param cf CF
     * @return
     */
    List<OracleReworkScrapCell> getOracleReworkScrapCell(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
                                                         @Param("prodId") String prodId, @Param("tft") String tft, @Param("cf") String cf);


    /**
     * 获取LCM1的重工报废数据
     * @param fromDate 投产时间
     * @param toDate 产出时间
     * @param wo 工单
     * @return
     */
    List<OracleReworkScrapLcm> getOracleReworkScrapLcm1(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
                                                        @Param("wo") String wo);

    /**
     * 获取LCM2的重工报废数据
     * @param fromDate 投产时间
     * @param toDate 产出时间
     * @param wo 工单
     * @return
     */
    List<OracleReworkScrapLcm> getOracleReworkScrapLcm2(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
                                                         @Param("wo") String wo);
}
