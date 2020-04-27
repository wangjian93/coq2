package com.ivo.coq.costCategory.reworkScrap.service;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostArray;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostCell;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostLcm;

import java.util.List;

/**
 * Array、cell、lcm三厂的重工报废明细数据服务接口
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCostDetailService {

    /**
     * 获取Array的重工报废明细数据
     * @param project 机种
     * @return
     */
    List<ReworkScrapCostArray> getReworkScrapCostArray(String project);
    List<ReworkScrapCostArray> getReworkScrapCostArray(String project, String stage, Integer time);



    /**
     * 获取Cell的重工报废明细数据
     * @param project 机种
     * @return
     */
    List<ReworkScrapCostCell> getReworkScrapCostCell(String project);
    List<ReworkScrapCostCell> getReworkScrapCostCell(String project, String stage, Integer time);


    /**
     * 获取LCM的重工报废明细数据
     * @param project 机种
     * @return
     */
    List<ReworkScrapCostLcm> getReworkScrapCostLcm(String project);
    List<ReworkScrapCostLcm> getReworkScrapCostLcm(String project, String stage, Integer time);


    void saveArray(List<ReworkScrapCostArray> list);

    void saveCell(List<ReworkScrapCostCell> list);

    void saveLcm(List<ReworkScrapCostLcm> list);

    void deleteArray(Long jobId);

    void deleteCell(Long jobId);

    void deleteLcm(Long jobId);

    /**
     * 计算Array、cell、lcm三厂的重工报废费用明细
     * @param project 机种
     */
    void computeReworkScrapCostDetail(String project);
}
