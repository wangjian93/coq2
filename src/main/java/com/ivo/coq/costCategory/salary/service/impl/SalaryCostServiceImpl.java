package com.ivo.coq.costCategory.salary.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.salary.entity.SalaryCostDetail;
import com.ivo.coq.costCategory.salary.entity.SalaryCostNormalHoursDetail;
import com.ivo.coq.costCategory.salary.service.SalaryCostDetailService;
import com.ivo.coq.costCategory.salary.service.SalaryCostNormalHoursDetailService;
import com.ivo.coq.project.entity.Milestone;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.costCategory.salary.entity.SalaryCost;
import com.ivo.coq.costCategory.salary.repository.SalaryCostRepository;
import com.ivo.coq.costCategory.salary.service.SalaryCostService;
import com.ivo.coq.project.service.MilestoneService;
import com.ivo.coq.project.service.StageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    private MilestoneService milestoneService;

    @Autowired
    public SalaryCostServiceImpl(SalaryCostRepository repository, StageService stageService,
                                 SalaryCostDetailService salaryCostDetailService,
                                 SalaryCostNormalHoursDetailService salaryCostNormalHoursDetailService,
                                 MilestoneService milestoneService) {
        this.repository = repository;
        this.stageService = stageService;
        this.salaryCostDetailService = salaryCostDetailService;
        this.salaryCostNormalHoursDetailService = salaryCostNormalHoursDetailService;
        this.milestoneService = milestoneService;
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
        //判断是否有NPRB/Design阶段
        boolean NPRB_is = false;
        boolean Design_is = false;
        List<Milestone> milestoneList =  milestoneService.getMilestone(project);
        for(Milestone milestone : milestoneList) {
            if(StringUtils.equalsIgnoreCase(milestone.getMilestone(), "NPRB")) {
                if(milestone.getEndDate().before(new Date())) {
                    NPRB_is = true;
                }
            } else if(StringUtils.equalsIgnoreCase(milestone.getMilestone(), "DESIGN REVIEW")) {
                if(milestone.getEndDate().before(new Date())) {
                    Design_is = true;
                }
            }
        }

        List<SalaryCost> salaryCostList = new ArrayList<>();
        for(Stage stage : stageList) {
            if(stage.getStage().equals(StageEnum.NPRB.getStage()) && !NPRB_is) continue;
            if(stage.getStage().equals(StageEnum.Design.getStage()) && !Design_is) continue;
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
        if(nprbSalaryCost != null) {
            List<SalaryCostDetail> salaryCostDetailList = salaryCostDetailService.getSalaryCostDetail(project);
            Double preventionAmount = null;
            Double inLossAmount = null;
            for(SalaryCostDetail salaryCostDetail : salaryCostDetailList) {
                preventionAmount = DoubleUtil.sum(preventionAmount, salaryCostDetail.getPreventionAmount());
                inLossAmount = DoubleUtil.sum(inLossAmount, salaryCostDetail.getInLossAmount());
            }
            nprbSalaryCost.setPreventionAmount(preventionAmount);
            nprbSalaryCost.setInLossAmount(inLossAmount);
            nprbSalaryCost.setAmount(DoubleUtil.sum(preventionAmount, inLossAmount));
            repository.save(nprbSalaryCost);
        }
        SalaryCost designSalaryCost = getSalaryCost(project, StageEnum.Design.getStage(), null);
        if(designSalaryCost != null) {
            List<SalaryCostNormalHoursDetail> salaryCostNormalHoursDetailList = salaryCostNormalHoursDetailService.getSalaryCostNormalHoursDetail(project);
            Double preventionAmount_ = null;
            Double inLossAmount_ = null;
            for(SalaryCostNormalHoursDetail salaryCostNormalHoursDetail : salaryCostNormalHoursDetailList) {
                preventionAmount_ = DoubleUtil.sum(preventionAmount_, salaryCostNormalHoursDetail.getPreventionAmount());
                inLossAmount_ = DoubleUtil.sum(inLossAmount_, salaryCostNormalHoursDetail.getInLossAmount());
            }
            designSalaryCost.setPreventionAmount(preventionAmount_);
            designSalaryCost.setInLossAmount(inLossAmount_);
            designSalaryCost.setAmount(DoubleUtil.sum(preventionAmount_, inLossAmount_));
            repository.save(designSalaryCost);
        }
    }
}
