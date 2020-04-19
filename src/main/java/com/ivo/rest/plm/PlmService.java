package com.ivo.rest.plm;

import com.ivo.rest.plm.entity.PlmMember;
import com.ivo.rest.plm.entity.PlmMilestone;
import com.ivo.rest.plm.entity.PlmSample;

import java.util.List;

/**
 * PLM数据接口服务
 * @author wj
 * @version 1.0
 */
public interface PlmService {

    /**
     * 获取机种在PLM中的实验管理信息
     * @param project 机种
     * @return List<PlmSample
     */
    List<PlmSample> getProjectSample(String project);

    /**
     * 获取机种在PLM中的项目成员
     * @param project 机种
     * @return List<PlmMember>
     */
    List<PlmMember> getMember(String project);

    /**
     * 获取机种在PLM中的进度
     * @param project 机种
     * @return List<PlmMilestone>
     */
    List<PlmMilestone> getMilestone(String project);

}
