package com.ivo.coq.cost.service;

import com.ivo.coq.cost.entity.CostSubject;

import java.util.List;
import java.util.Map;

/**
 * 机种成本详细(二级科目) 服务接口
 * @author wj
 * @version 1.0
 */
public interface CostSubjectService {

    /**
     * 获取机种的二级科目列表
     * @param project 机种
     * @return List<CostSubject>
     */
    List<CostSubject> getCostSubjects(String project);

    /**
     * 创建机种机种的二级科目
     * @param project 机种
     */
    void createCostSubject(String project);

    /**
     * 计算机种的二级科目
     * @param project 机种
     */
    void computeCostSubject(String project);

    /**
     * 获取机种的二级科目，数据格式改变为行专列
     * @param project 机种
     * @return List<Map>
     */
    List<Map> getCostSubjectsConvertMap(String project);
}
