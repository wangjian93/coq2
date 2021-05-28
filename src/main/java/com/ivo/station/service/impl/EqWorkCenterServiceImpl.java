package com.ivo.station.service.impl;

import com.ivo.station.entity.EqWorkCenter;
import com.ivo.station.repository.EqWorkCenterRepository;
import com.ivo.station.service.EqWorkCenterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class EqWorkCenterServiceImpl implements EqWorkCenterService {

    @Resource
    private EqWorkCenterRepository eqWorkCenterRepository;

    @Override
    public String getCostCenterByEq(String eq, String fab) {
        EqWorkCenter eqWorkCenter = eqWorkCenterRepository.findFirstByEqAndFab(eq, fab);
        return eqWorkCenter==null ? null : eqWorkCenter.getCost_center();
    }

    @Override
    public String getCostCenterByWorkCenter(String workCenter) {
        EqWorkCenter eqWorkCenter = eqWorkCenterRepository.findFirstByWorkCenterAndFab(workCenter, "LCM");
        return eqWorkCenter==null ? null : eqWorkCenter.getCost_center();
    }
}
