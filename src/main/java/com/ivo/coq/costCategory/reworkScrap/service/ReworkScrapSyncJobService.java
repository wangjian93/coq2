package com.ivo.coq.costCategory.reworkScrap.service;

/**
 * 重工报废数据同步任务处理
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapSyncJobService {

    /**
     * 生成重工报废数据同步任务
     * @param project 机种
     */
    void generateJob(String project);

    /**
     * 执行机种的重工报废数据同步任务
     * @param project 机种
     */
    void runJob(String project);
}
