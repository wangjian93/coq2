package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.entity.EngineeringExperimentMaterial;
import com.ivo.coq.project.entity.EngineeringExperimentProduct;
import com.ivo.coq.project.entity.EngineeringExperimentWo;

import java.util.List;

/**
 * 机种的工程实验信息服务接口
 * @author wj
 * @version 1.0
 */
public interface EngineeringExperimentService  {

    /**
     * 获取机种的EE信息列表
     * @param project 机种
     * @return List<EngineeringExperiment>
     */
    List<EngineeringExperiment> getEngineeringExperiments(String project);

    /**
     * 获取EE产品
     * @param project 机种
     * @return List
     */
    List<EngineeringExperimentProduct> getEngineeringExperimentProduct(String project);

    /**
     * 获取EE料号
     * @param project 机种
     * @return List
     */
    List<EngineeringExperimentMaterial> getEngineeringExperimentMaterial(String project);

    /**
     * 获取EE工单
     * @param project 机种
     * @return
     */
    List<EngineeringExperimentWo> getEngineeringExperimentWo(String project);

    /**
     * 生成工程实验单信息
     * @param project 机种
     */
    void generateEngineeringExperiment(String project);

    /**
     * 同步获取工程实验单的厂别
     * @param project 机种
     */
    void syncEngineeringExperimentPlant(String project);

    /**
     * 同步获取工程实验单（ARRAY/CELL）的产品
     * @param project 机种
     */
    void syncEngineeringExperimentProduct(String project);

    /**
     * 同步获取工程实验单（CELL）的料号
     * @param project 机种
     */
    void syncEngineeringExperimentMaterial(String project);

    /**
     * 同步获取工程试验单（LCM）的工单
     * @param project 机种
     */
    void syncEngineeringExperimentWo(String project);
}
