package com.ivo.coq.cost.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.cost.entity.Cost;
import com.ivo.coq.cost.entity.CostSubject;
import com.ivo.coq.cost.repository.CostRepository;
import com.ivo.coq.cost.service.CostService;
import com.ivo.coq.cost.service.CostSubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class CostServiceImpl implements CostService {

    private static Logger logger = LoggerFactory.getLogger(CostServiceImpl.class);

    private CostRepository repository;

    private CostSubjectService costSubjectService;

    @Autowired
    public CostServiceImpl(CostRepository repository, CostSubjectService costSubjectService) {
        this.repository = repository;
        this.costSubjectService = costSubjectService;
    }

    @Override
    public Cost getCost(String project) {
        return repository.findFirstByProject(project);
    }

    @Override
    public void createCost(String project) {
        logger.info("创建 Cost >>" + project);
        Cost cost = new Cost(project);
        repository.save(cost);
    }

    @Override
    public void computeCost(String project) {
        logger.info("计算 Cost >>" + project);
        List<CostSubject> costSubjectList = costSubjectService.getCostSubjects(project);

        // 预防费用
        Double preventionCost = null;
        // 鉴定费用
        Double identityCost = null;
        // 内损费用
        Double inLossCost = null;
        // 外损费用
        Double outLossCost = null;
        for(CostSubject costSubject : costSubjectList) {
            preventionCost = DoubleUtil.sum(preventionCost, costSubject.getPreventionCost());
            identityCost = DoubleUtil.sum(identityCost, costSubject.getIdentityCost());
            inLossCost = DoubleUtil.sum(inLossCost, costSubject.getInLossCost());
            outLossCost = DoubleUtil.sum(outLossCost, costSubject.getOutLossCost());
        }
        // 必要费用
        Double necessaryCost = DoubleUtil.sum(preventionCost, identityCost);
        // 多余费用
        Double redundantCost = DoubleUtil.sum(inLossCost, outLossCost);

        Cost cost = getCost(project);
        cost.setPreventionCost(preventionCost);
        cost.setIdentityCost(identityCost);
        cost.setInLossCost(inLossCost);
        cost.setOutLossCost(outLossCost);
        cost.setNecessaryCost(necessaryCost);
        cost.setRedundantCost(redundantCost);

        repository.save(cost);
    }
}
