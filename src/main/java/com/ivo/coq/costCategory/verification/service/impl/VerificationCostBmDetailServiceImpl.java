package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.coq.costCategory.verification.entity.VerificationCostBmDetail;
import com.ivo.coq.costCategory.verification.repository.VerificationCostBmDetailRepository;
import com.ivo.coq.costCategory.verification.service.VerificationCostBmDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class VerificationCostBmDetailServiceImpl implements VerificationCostBmDetailService {

    private VerificationCostBmDetailRepository repository;

    @Autowired
    public VerificationCostBmDetailServiceImpl(VerificationCostBmDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VerificationCostBmDetail> getVerificationCostBmDetail(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<VerificationCostBmDetail> getVerificationCostBmDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }
}
