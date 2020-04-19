package com.ivo.coq.costCategory.oba.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.enums.StageEnum;
import com.ivo.coq.costCategory.oba.entity.ObaCostDetail;
import com.ivo.coq.costCategory.oba.repository.ObaCostDetailRepository;
import com.ivo.coq.costCategory.oba.service.ObaCostDetailService;
import com.ivo.coq.project.entity.Milestone;
import com.ivo.coq.project.service.MilestoneService;
import com.ivo.rest.qms.QmsService;
import com.ivo.rest.qms.entity.QmsOba;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class ObaCostDetailServiceImpl implements ObaCostDetailService {

    private ObaCostDetailRepository repository;

    private QmsService qmsService;

    private MilestoneService milestoneService;

    @Autowired
    public ObaCostDetailServiceImpl(ObaCostDetailRepository repository, QmsService qmsService,
                                    MilestoneService milestoneService) {
        this.repository = repository;
        this.qmsService = qmsService;
        this.milestoneService = milestoneService;
    }

    @Override
    public List<ObaCostDetail> getObaCostDetail(String project) {
        return repository.findByProject(project);
    }

    @Override
    public List<ObaCostDetail> getObaCostDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void syncObaCostDetail(String project) {
        log.info("从QMS同步机种的OBA明细" + project);
        repository.deleteAll(getObaCostDetail(project));
        List<QmsOba> qmsObaList = qmsService.getQmsOba(project);
        if(qmsObaList == null) return;
        List<ObaCostDetail> obaCostDetailList = new ArrayList<>();
        for(QmsOba qmsOba : qmsObaList) {
            ObaCostDetail obaCostDetail = new ObaCostDetail(project);
            obaCostDetail.setPeNumber(qmsOba.getPeNumber().trim());
            obaCostDetail.setHappenTime(qmsOba.getHappenTime());
            obaCostDetail.setObaType(qmsOba.getObaType().trim());
            obaCostDetail.setQuantity(qmsOba.getQuantity());
            obaCostDetail.setPrice(qmsOba.getPrice());
            obaCostDetail.setAmount(qmsOba.getAmount());
            obaCostDetailList.add(obaCostDetail);
        }

        // 确定阶段：按照发生时间确定阶段, 全部默认到阶段的次数1中

        Date[] nprb_date = milestoneService.getStageAndEndDate(project, StageEnum.NPRB.getStage());
        Date[] design_date = milestoneService.getStageAndEndDate(project, StageEnum.Design.getStage());
        Date[] evt_date = milestoneService.getStageAndEndDate(project, StageEnum.EVT.getStage());
        Date[] dvt_date = milestoneService.getStageAndEndDate(project, StageEnum.DVT.getStage());
        Date[] pvt_date = milestoneService.getStageAndEndDate(project, StageEnum.PVT.getStage());
        Date[] mp_date = milestoneService.getStageAndEndDate(project, StageEnum.MP.getStage());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(ObaCostDetail obaCostDetail : obaCostDetailList) {
            Integer time = 1;
            String stage = null;
            Date happenTime = null;
            try {
                happenTime = sdf.parse(obaCostDetail.getHappenTime());
            } catch (ParseException e) {
                continue;
            }

            if(nprb_date[0]!=null && nprb_date[1]!=null) {
                if(!happenTime.before(nprb_date[0]) && happenTime.before(nprb_date[1])) {
                    stage = StageEnum.NPRB.getStage();
                }
            } else if(design_date[0]!=null && design_date[1]!=null) {
                if(!happenTime.before(design_date[0]) && happenTime.before(design_date[1])) {
                    stage = StageEnum.Design.getStage();
                }
            } else if(evt_date[0]!=null && evt_date[1]!=null) {
                if(!happenTime.before(evt_date[0]) && happenTime.before(evt_date[1])) {
                    stage = StageEnum.DVT.getStage();
                }
            } else if(dvt_date[0]!=null && dvt_date[1]!=null) {
                if(!happenTime.before(dvt_date[0]) && happenTime.before(dvt_date[1])) {
                    stage = StageEnum.EVT.getStage();
                }
            } else if(pvt_date[0]!=null && pvt_date[1]!=null) {
                if(!happenTime.before(pvt_date[0]) && happenTime.before(pvt_date[1])) {
                    stage = StageEnum.PVT.getStage();
                }
            } else if(mp_date[0]!=null && mp_date[1]!=null) {
                if(!happenTime.before(mp_date[0]) && happenTime.before(mp_date[1])) {
                    stage = StageEnum.MP.getStage();
                }
            }
            obaCostDetail.setStage(stage);
            obaCostDetail.setTime(time);
        }
        repository.saveAll(obaCostDetailList);
    }
}
