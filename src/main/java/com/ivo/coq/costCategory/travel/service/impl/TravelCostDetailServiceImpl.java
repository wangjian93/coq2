package com.ivo.coq.costCategory.travel.service.impl;

import com.ivo.coq.costCategory.travel.entity.TravelCostDetail;
import com.ivo.coq.costCategory.travel.repository.TravelCostDetailRepository;
import com.ivo.coq.costCategory.travel.service.TravelCostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class TravelCostDetailServiceImpl implements TravelCostDetailService {

    private TravelCostDetailRepository repository;

    @Autowired
    public TravelCostDetailServiceImpl(TravelCostDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TravelCostDetail> getTravelCostDetail(String project) {
        return repository.findByProjectOrderById(project);
    }
}
