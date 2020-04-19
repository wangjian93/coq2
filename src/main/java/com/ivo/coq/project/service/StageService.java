package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.Stage;

import java.util.List;

/**
 * 机种的阶段服务接口
 * @author wj
 * @version 1.0
 */
public interface StageService {

    /**
     * 获取机种有哪些阶段
     * @param project 机种
     * @return
     */
    List<Stage> getStage(String project);

    /**
     * 机种阶段根据实验信息生成
     * @param project 机种
     */
    void generateStage(String project);

    /**
     * 获取机种的阶段忽略次数
     * @return List<String>
     */
    List<String> getStageNoTime(String project);
}
