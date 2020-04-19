package com.ivo.coq.project.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.coq.project.entity.Milestone;
import com.ivo.coq.project.repository.MilestoneRepository;
import com.ivo.coq.project.service.MilestoneService;
import com.ivo.rest.plm.PlmService;
import com.ivo.rest.plm.entity.PlmMilestone;
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
@Slf4j
@Service
public class MilestoneServiceImpl implements MilestoneService {

    private PlmService plmService;

    private MilestoneRepository repository;

    @Autowired
    public MilestoneServiceImpl(PlmService plmService, MilestoneRepository repository) {
        this.plmService = plmService;
        this.repository = repository;
    }

    @Override
    public List<Milestone> getMilestone(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public int getDelayDays(String project, String stage) {

        return 0;
    }

    @Override
    public void syncMilestone(String project) {
        log.info("从PLM同步机种的进度 " + project);
        List<PlmMilestone> plmMilestoneList = plmService.getMilestone(project);
        repository.deleteAll(getMilestone(project));
        List<Milestone> milestoneList = new ArrayList<>();
        for(PlmMilestone plmMilestone : plmMilestoneList) {
            Milestone milestone = new Milestone();
            milestone.setProject(project.trim().toUpperCase());
            milestone.setMilestone(plmMilestone.getMilestone().trim().toUpperCase());
            milestone.setEndDate(plmMilestone.getEndDate());
            milestone.setEndDatePlan(plmMilestone.getEndDatePlan());
            milestoneList.add(milestone);
        }
        repository.saveAll(milestoneList);
    }

    @Override
    public Date[] getStageAndEndDate(String project, String stage) {
        // 各阶段的日期范围
        // NPRB : KICK OFF ~  NPRB
        // DESIGN : NPRB ~ DESIGN REVIEW
        // EVT ：DESIGN REVIEW ~ EVT
        // DVT ： EVT ~ DVT
        // PVT ：DVT ~ PVT
        // MP ： PVT ~ MP
        Date fromDate = null;
        Date toDate = null;
        if(StringUtils.equalsIgnoreCase(stage, StageEnum.NPRB.getStage())) {
            Milestone milestone1 = getMilestone(project, "KICK OFF");
            Milestone milestone2 = getMilestone(project, "NPRB");
            if(milestone1 != null) fromDate = milestone1.getEndDate();
            if(milestone2 != null) toDate = milestone2.getEndDate();
        } else if(StringUtils.equalsIgnoreCase(stage, StageEnum.Design.getStage())) {
            Milestone milestone1 = getMilestone(project, "NPRB");
            Milestone milestone2 = getMilestone(project, "DESIGN REVIEW");
            if(milestone1 != null) fromDate = milestone1.getEndDate();
            if(milestone2 != null) toDate = milestone2.getEndDate();
        } else if(StringUtils.equalsIgnoreCase(stage, StageEnum.EVT.getStage())) {
            Milestone milestone1 = getMilestone(project, "DESIGN REVIEW");
            Milestone milestone2 = getMilestone(project, "EVT");
            if(milestone1 != null) fromDate = milestone1.getEndDate();
            if(milestone2 != null) toDate = milestone2.getEndDate();
        } else if(StringUtils.equalsIgnoreCase(stage, StageEnum.DVT.getStage())) {
            Milestone milestone1 = getMilestone(project, "EVT");
            Milestone milestone2 = getMilestone(project, "DVT");
            if(milestone1 != null) fromDate = milestone1.getEndDate();
            if(milestone2 != null) toDate = milestone2.getEndDate();
        } else if(StringUtils.equalsIgnoreCase(stage, StageEnum.PVT.getStage())) {
            Milestone milestone1 = getMilestone(project, "DVT");
            Milestone milestone2 = getMilestone(project, "PVT");
            if(milestone1 != null) fromDate = milestone1.getEndDate();
            if(milestone2 != null) toDate = milestone2.getEndDate();
        } else if(StringUtils.equalsIgnoreCase(stage, StageEnum.MP.getStage())) {
            Milestone milestone1 = getMilestone(project, "PVT");
            Milestone milestone2 = getMilestone(project, "MP");
            if(milestone1 != null) fromDate = milestone1.getEndDate();
            if(milestone2 != null) toDate = milestone2.getEndDate();
        }
        
        return new Date[] {fromDate, toDate};
    }

    private Milestone getMilestone(String project, String milestone) {
        return repository.findByProjectAndMilestone(project, milestone);
    }
}
