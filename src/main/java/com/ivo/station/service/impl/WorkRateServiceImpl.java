package com.ivo.station.service.impl;

import com.ivo.station.entity.WorkRate;
import com.ivo.station.repository.WorkRateRepository;
import com.ivo.station.service.WorkRateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class WorkRateServiceImpl implements WorkRateService {

    @Resource
    private WorkRateRepository workRateRepository;

    @Override
    public List<WorkRate> getWorkRate(String costCenter) {
        return workRateRepository.findByCostCenter(costCenter);
    }

    @Override
    public double getTotalPrice(String costCenter) {
        return workRateRepository.getTotalPrice(costCenter);
    }

    @Override
    public double getM_price(String costCenter) {
        return workRateRepository.getM_price(costCenter);
    }

    @Override
    public double getOther_price(String costCenter) {
        return workRateRepository.getOther_price(costCenter);
    }
}
