package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.Sample;

import java.util.List;

/**
 * 机种的实验管理信息服务接口
 * @author wj
 * @version 1.0
 */
public interface SampleService {

    /**
     * 根据机种获取机种的实验信息列表
     * @param project 机种
     * @return List<Sample>
     */
    List<Sample> getSamples(String project);


    /**
     * 从PLM同步机种的实验管理信息
     * @param project 机种
     */
    void syncSamples(String project);
}
