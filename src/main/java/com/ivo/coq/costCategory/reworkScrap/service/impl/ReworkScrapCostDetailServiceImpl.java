package com.ivo.coq.costCategory.reworkScrap.service.impl;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostDetail;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostDetailRepository;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ReworkScrapCostDetailServiceImpl implements ReworkScrapCostDetailService {

    private ReworkScrapCostDetailRepository repository;

    @Autowired
    public ReworkScrapCostDetailServiceImpl(ReworkScrapCostDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ReworkScrapCostDetail> getReworkScrapCostDetail(String project) {
        return repository.findByProjectOrderById(project);
    }
}
