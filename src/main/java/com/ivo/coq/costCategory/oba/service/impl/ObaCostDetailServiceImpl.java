package com.ivo.coq.costCategory.oba.service.impl;

import com.ivo.coq.costCategory.oba.entity.ObaCostDetail;
import com.ivo.coq.costCategory.oba.repository.ObaCostDetailRepository;
import com.ivo.coq.costCategory.oba.service.ObaCostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ObaCostDetailServiceImpl implements ObaCostDetailService {

    private ObaCostDetailRepository repository;

    @Autowired
    public ObaCostDetailServiceImpl(ObaCostDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ObaCostDetail> getObaCostDetail(String project) {
        return repository.findByProjectOrderById(project);
    }
}
