package com.ivo.coq.project.service;

import com.ivo.coq.project.entity.Milestone;

import java.util.Date;
import java.util.List;

/**
 * 机种进度服务接口
 * @author wj
 * @version 1.0
 */
public interface MilestoneService {

    /**
     * 获取机种的进度
     * @param project 机种
     * @return List<Milestone>
     */
    List<Milestone> getMilestone(String project);

    /**
     * 获取机种阶段的Delay天数
     * @param project 机种
     * @param stage 阶段
     * @return Delay天数
     */
    int getDelayDays(String project, String stage);

    /**
     * 从PLM同步机种的进度
     * @param project 机种
     */
    void syncMilestone(String project);

    /**
     * 获取阶段的起始和结束日期
     * @param project 机种
     * @param stage 阶段
     * @return Date[] = {开始日期，结束日期}
     */
    Date[] getStageAndEndDate(String project, String stage);


}
