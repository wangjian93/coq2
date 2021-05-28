package com.ivo.np.service.impl;

import com.ivo.np.entity.FailureCostRate;
import com.ivo.np.repository.FailureCostRateRepository;
import com.ivo.np.service.FailureCostRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class FailureCostRateServiceImpl implements FailureCostRateService {

    @Autowired
    private FailureCostRateRepository failureCostRateRepository;

    @Override
    public List<FailureCostRate> getFailureCostRate(String fromMonth, String toMonth) {
        return failureCostRateRepository.findByMonthGreaterThanEqualAndMonthLessThanEqual(fromMonth, toMonth);
    }
}
