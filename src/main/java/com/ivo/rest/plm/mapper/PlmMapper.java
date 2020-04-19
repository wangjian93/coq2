package com.ivo.rest.plm.mapper;

import com.ivo.rest.plm.entity.PlmMember;
import com.ivo.rest.plm.entity.PlmMilestone;
import com.ivo.rest.plm.entity.PlmSample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PLM 获取数据接口
 * @author wj
 * @version 1.0
 */
@Repository
public interface PlmMapper {

    List<PlmSample> getProjectSample(String project);

    List<PlmMember> getMember(String project);

    List<PlmMilestone> getMilestone(String project);
}
