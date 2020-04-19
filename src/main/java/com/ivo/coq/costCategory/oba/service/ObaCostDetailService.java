package com.ivo.coq.costCategory.oba.service;

import com.ivo.coq.costCategory.oba.entity.ObaCostDetail;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ObaCostDetailService {

    /**
     * 获取机种的OBA明细
     * @param project 机种
     * @return List<ObaCostDetail>
     */
    List<ObaCostDetail> getObaCostDetail(String project);

    /**
     * 获取机种的OBA明细
     * @param project 机种
     * @param stage 阶段
     * @param time 次数
     * @return List<ObaCostDetail>
     */
    List<ObaCostDetail> getObaCostDetail(String project, String stage, Integer time);

    /**
     * 从QMS同步机种的OBA明细
     * @param project 机种
     */
    void syncObaCostDetail(String project);
}
