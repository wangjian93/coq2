package com.ivo.rest.oracle.mapper;

import org.springframework.stereotype.Repository;

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
}
