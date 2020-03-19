package com.ivo.coq.costCategory.directMaterial.service.impl;

import com.ivo.coq.costCategory.directMaterial.entity.MaterialCostDetail;
import com.ivo.coq.costCategory.directMaterial.repository.MaterialCostDetailRepository;
import com.ivo.coq.costCategory.directMaterial.service.MaterialCostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class MaterialCostDetailServiceImpl implements MaterialCostDetailService {

    private MaterialCostDetailRepository repository;

    @Autowired
    public MaterialCostDetailServiceImpl(MaterialCostDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MaterialCostDetail> getMaterialCostDetails(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<MaterialCostDetail> getMaterialCostDetails(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }
}
