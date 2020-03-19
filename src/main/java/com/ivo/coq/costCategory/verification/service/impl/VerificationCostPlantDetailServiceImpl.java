package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.coq.costCategory.verification.entity.VerificationCostPlantDetail;
import com.ivo.coq.costCategory.verification.repository.VerificationCostPlantDetailRepository;
import com.ivo.coq.costCategory.verification.service.VerificationCostPlantDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class VerificationCostPlantDetailServiceImpl implements VerificationCostPlantDetailService {

    private VerificationCostPlantDetailRepository repository;

    @Autowired
    public VerificationCostPlantDetailServiceImpl(VerificationCostPlantDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VerificationCostPlantDetail> getVerificationCostPlantDetail(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<VerificationCostPlantDetail> getVerificationCostPlantDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }
}
