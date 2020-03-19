package com.ivo.coq.costCategory.production.service.impl;

import com.ivo.coq.costCategory.production.entity.ProductionCostDetail;
import com.ivo.coq.costCategory.production.repository.ProductionCostDetailRepository;
import com.ivo.coq.costCategory.production.service.ProductionCostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ProductionCostDetailServiceImpl implements ProductionCostDetailService {

    private ProductionCostDetailRepository repository;

    @Autowired
    public ProductionCostDetailServiceImpl(ProductionCostDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductionCostDetail> getProductionCostDetail(String project) {
        return repository.findByProjectOrderById(project);
    }
}
