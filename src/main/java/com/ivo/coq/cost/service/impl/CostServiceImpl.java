package com.ivo.coq.cost.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.cost.entity.Cost;
import com.ivo.coq.cost.entity.CostSubject;
import com.ivo.coq.cost.repository.CostRepository;
import com.ivo.coq.cost.service.CostService;
import com.ivo.coq.cost.service.CostSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class CostServiceImpl implements CostService {

    private CostRepository repository;

    private CostSubjectService costSubjectService;

    @Autowired
    public CostServiceImpl(CostRepository repository, CostSubjectService costSubjectService) {
        this.repository = repository;
        this.costSubjectService = costSubjectService;
    }

    @Override
    public Cost getCost(String project) {
        return repository.findFirstByProjectOrderById(project);
    }

    @Override
    public List<Cost> getCosts() {
        return repository.findAll();
    }

    @Override
    public void createCost(String project) {
        log.info("创建 Cost >>" + project);
        if(getCost(project) == null) {
            Cost cost = new Cost(project);
            repository.save(cost);
        } else {
            log.info(" Cost >>" + project + "已存在");
        }
    }

    @Override
    public void computeCost(String project) {
        log.info("计算 Cost >>" + project);
        List<CostSubject> costSubjectList = costSubjectService.getCostSubjects(project);

        // 预防费用
        Double preventionCost = null;
        // 鉴定费用
        Double identityCost = null;
        // 内损费用
        Double inLossCost = null;
        // 外损费用
        Double outLossCost = null;

        if(costSubjectList != null) {
            for(CostSubject costSubject : costSubjectList) {
                if(costSubject.getPreventionCost() != null && -1 != costSubject.getPreventionCost())
                    preventionCost = DoubleUtil.sum(preventionCost, costSubject.getPreventionCost());
                if(costSubject.getIdentityCost() != null && -1 != costSubject.getIdentityCost())
                    identityCost = DoubleUtil.sum(identityCost, costSubject.getIdentityCost());
                if(costSubject.getInLossCost() != null && -1 != costSubject.getInLossCost())
                    inLossCost = DoubleUtil.sum(inLossCost, costSubject.getInLossCost());
                if(costSubject.getOutLossCost() != null && -1 != costSubject.getOutLossCost())
                    outLossCost = DoubleUtil.sum(outLossCost, costSubject.getOutLossCost());
            }
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
