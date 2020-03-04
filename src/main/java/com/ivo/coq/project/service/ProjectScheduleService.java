package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.ProjectSchedule;

import java.util.Date;
import java.util.List;

/**
 * 机种的进度服务接口
 * @author wj
 * @version 1.0
 */
public interface ProjectScheduleService {

    /**
     * 获取机种的进度
     * @param project 机种名
     * @return List<ProjectSchedule>
     */
    List<ProjectSchedule> getSchedules(String project);

    /**
     * 获取机种某个阶段的日期
     * @param project 机种名
     * @param stage 机种阶段
     * @return Date
     */
    Date getStageDate(String project, String stage);
}
