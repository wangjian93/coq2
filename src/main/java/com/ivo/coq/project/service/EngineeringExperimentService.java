package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.EngineeringExperiment;

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
}
