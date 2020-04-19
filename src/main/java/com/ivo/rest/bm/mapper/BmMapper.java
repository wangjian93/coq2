package com.ivo.rest.bm.mapper;

import com.ivo.rest.bm.entity.BmModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Repository
public interface BmMapper {

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
     * @return List
     */
    List<BmModel> getBmJig(@Param("project") String project, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    /**
     * 获取机种的BM验证费用
     * @param project 机种
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return List
     */
    List<BmModel> getBmVerification(@Param("project") String project, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
