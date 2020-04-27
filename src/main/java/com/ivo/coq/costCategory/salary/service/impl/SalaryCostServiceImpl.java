package com.ivo.coq.costCategory.salary.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.salary.entity.SalaryCostDetail;
import com.ivo.coq.costCategory.salary.entity.SalaryCostNormalHoursDetail;
import com.ivo.coq.costCategory.salary.service.SalaryCostDetailService;
import com.ivo.coq.costCategory.salary.service.SalaryCostNormalHoursDetailService;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.costCategory.salary.entity.SalaryCost;
import com.ivo.coq.costCategory.salary.repository.SalaryCostRepository;
import com.ivo.coq.costCategory.salary.service.SalaryCostService;
import com.ivo.coq.project.service.StageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class SalaryCostServiceImpl implements SalaryCostService {

    private SalaryCostRepository repository;

    private StageService stageService;

    private SalaryCostDetailService salaryCostDetailService;

    private SalaryCostNormalHoursDetailService salaryCostNormalHoursDetailService;

    @Autowired
    public SalaryCostServiceImpl(SalaryCostRepository repository, StageService stageService,
                                 SalaryCostDetailService salaryCostDetailService,
                                 SalaryCostNormalHoursDetailService salaryCostNormalHoursDetailService) {
        this.repository = repository;
        this.stageService = stageService;
        this.salaryCostDetailService = salaryCostDetailService;
        this.salaryCostNormalHoursDetailService = salaryCostNormalHoursDetailService;
    }

    @Override
    public List<SalaryCost> getSalaryCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public SalaryCost getSalaryCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createSalaryCost(String project) {
        log.info("创建 SalaryCost >> " + project);
        repository.deleteAll(getSalaryCosts(project));
        List<Stage> stageList = stageService.getStage(project);
        List<SalaryCost> salaryCostList = new ArrayList<>();
        for(Stage stage : stageList) {
            SalaryCost salaryCost = new SalaryCost(stage.getProject(), stage.getStage(), stage.getTime());
            // 人员工资费用只有NPRB/DESIGN阶段
            if(!StringUtils.equalsAnyIgnoreCase(stage.getStage(), StageEnum.NPRB.getStage(), StageEnum.Design.getStage())) {
                salaryCost.setInLossAmount(-1D);
                salaryCost.setPreventionAmount(-1D);
                salaryCost.setAmount(-1D);
            }
            salaryCostList.add(salaryCost);
        }
        repository.saveAll(salaryCostList);
    }

    @Override
    public void computeSalaryCost(String project) {
        log.info("计算 SalaryCost >> " + project);
        // 人员薪资算入NPRB阶段
        // 标准工时薪资算入Design阶段
        SalaryCost nprbSalaryCost = getSalaryCost(project, StageEnum.NPRB.getStage(), null);
        SalaryCost designSalaryCost = getSalaryCost(project, StageEnum.Design.getStage(), null);

        List<SalaryCostDetail> salaryCostDetailList = salaryCostDetailService.getSalaryCostDetail(project);
        List<SalaryCostNormalHoursDetail> salaryCostNormalHoursDetailList = salaryCostNormalHoursDetailService.getSalaryCostNormalHoursDetail(project);

        Double preventionAmount = null;
        Double inLossAmount = null;
        for(SalaryCostDetail salaryCostDetail : salaryCostDetailList) {
            preventionAmount = DoubleUtil.sum(preventionAmount, salaryCostDetail.getPreventionAmount());
            inLossAmount = DoubleUtil.sum(inLossAmount, salaryCostDetail.getInLossAmount());
        }
        nprbSalaryCost.setPreventionAmount(preventionAmount);
        nprbSalaryCost.setInLossAmount(inLossAmount);
        nprbSalaryCost.setAmount(DoubleUtil.sum(preventionAmount, inLossAmount));

        Double preventionAmount_ = null;
        Double inLossAmount_ = null;
        for(SalaryCostNormalHoursDetail salaryCostNormalHoursDetail : salaryCostNormalHoursDetailList) {
            preventionAmount_ = DoubleUtil.sum(preventionAmount_, salaryCostNormalHoursDetail.getPreventionAmount());
            inLossAmount_ = DoubleUtil.sum(inLossAmount_, salaryCostNormalHoursDetail.getInLossAmount());
        }
        designSalaryCost.setPreventionAmount(preventionAmount_);
        designSalaryCost.setInLossAmount(inLossAmount_);
        designSalaryCost.setAmount(DoubleUtil.sum(preventionAmount_, inLossAmount_));

        repository.save(nprbSalaryCost);
        repository.save(designSalaryCost);
    }
}
