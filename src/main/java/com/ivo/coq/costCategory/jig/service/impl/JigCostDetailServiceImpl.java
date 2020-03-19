package com.ivo.coq.costCategory.jig.service.impl;

import com.ivo.coq.costCategory.jig.entity.JigCostDetail;
import com.ivo.coq.costCategory.jig.repository.JigCostDetailRepository;
import com.ivo.coq.costCategory.jig.service.JigCostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class JigCostDetailServiceImpl implements JigCostDetailService {

    private JigCostDetailRepository repository;

    @Autowired
    public JigCostDetailServiceImpl(JigCostDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<JigCostDetail> getJigCostDetail(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<JigCostDetail> getJigCostDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }
}
