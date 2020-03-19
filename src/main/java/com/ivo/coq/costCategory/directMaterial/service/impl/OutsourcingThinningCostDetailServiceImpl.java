package com.ivo.coq.costCategory.directMaterial.service.impl;

import com.ivo.coq.costCategory.directMaterial.entity.OutsourcingThinningCostDetail;
import com.ivo.coq.costCategory.directMaterial.repository.OutsourcingThinningCostDetailRepository;
import com.ivo.coq.costCategory.directMaterial.service.OutsourcingThinningCostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class OutsourcingThinningCostDetailServiceImpl implements OutsourcingThinningCostDetailService {

    private OutsourcingThinningCostDetailRepository repository;

    @Autowired
    public OutsourcingThinningCostDetailServiceImpl(OutsourcingThinningCostDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OutsourcingThinningCostDetail> getOutsourcingThinningCostDetails(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<OutsourcingThinningCostDetail> getOutsourcingThinningCostDetails(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }
}
