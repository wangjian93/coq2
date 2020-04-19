package com.ivo.rest.plm;

import com.ivo.rest.plm.entity.PlmMember;
import com.ivo.rest.plm.entity.PlmMilestone;
import com.ivo.rest.plm.entity.PlmSample;
import com.ivo.rest.plm.mapper.PlmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class PlmServiceImpl implements PlmService {

    private PlmMapper plmMapper;

    @Autowired
    public PlmServiceImpl(PlmMapper plmMapper) {
        this.plmMapper = plmMapper;
    }

    @Override
    public List<PlmSample> getProjectSample(String project) {
        return plmMapper.getProjectSample(project);
    }

    @Override
    public List<PlmMember> getMember(String project) {
        return plmMapper.getMember(project);
    }

    @Override
    public List<PlmMilestone> getMilestone(String project) {
        return plmMapper.getMilestone(project);
    }
}
