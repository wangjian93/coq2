package com.ivo.coq.costCategory.jig.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.coq.costCategory.jig.entity.JigCostDetail;
import com.ivo.coq.costCategory.jig.repository.JigCostDetailRepository;
import com.ivo.coq.costCategory.jig.service.JigCostDetailService;
import com.ivo.coq.project.service.MilestoneService;
import com.ivo.coq.project.service.StageService;
import com.ivo.rest.bm.BmService;
import com.ivo.rest.bm.entity.BmModel;
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
public class JigCostDetailServiceImpl implements JigCostDetailService {

    private JigCostDetailRepository repository;

    private BmService bmService;

    private StageService stageService;

    private MilestoneService milestoneService;

    @Autowired
    public JigCostDetailServiceImpl(JigCostDetailRepository repository, BmService bmService,
                                    StageService stageService, MilestoneService milestoneService) {
        this.repository = repository;
        this.bmService = bmService;
        this.stageService = stageService;
        this.milestoneService = milestoneService;
    }

    @Override
    public List<JigCostDetail> getJigCostDetail(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<JigCostDetail> getJigCostDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }

    @Override
    public void syncJigCostDetail(String project) {
        log.info("从BM同步治工具费用 " + project);
        repository.deleteAll(getJigCostDetail(project));
        List<String> stages = stageService.getStageNoTime(project);
        if(stages == null) return;
        for(String stage : stages) {
            Date[] dates = milestoneService.getStageAndEndDate(project, stage);
            if(dates == null || dates.length < 2) continue;
            Date fromDate = dates[0];
            Date toDate = dates[1];
            if(fromDate==null || toDate ==null) continue;
            List<BmModel> bmModelList = bmService.getBmJig(project, fromDate, toDate);
            if(bmModelList == null) continue;
            List<JigCostDetail> jigCostDetailList = new ArrayList<>();
            for(BmModel b : bmModelList) {
                // 治工具费用先全部默认算到机种阶段的次数1中
                Integer time = 1;
                //默认全部算预防
                String type = "预防";
                if(StringUtils.equalsAnyIgnoreCase(stage, StageEnum.NPRB.getStage(), StageEnum.Design.getStage())) time = null;
                JigCostDetail jigCostDetail = new JigCostDetail(project, stage, time);
                jigCostDetail.setType(type);
                jigCostDetail.setPr(b.getPr().trim());
                jigCostDetail.setPrDrafter(b.getPrDrafter().trim());
                jigCostDetail.setPrDrafterDate(b.getPrDrafterDate());
                jigCostDetail.setPrItem(b.getPrItem().trim());
                jigCostDetail.setBudgetType(b.getBudgetType().trim());
                jigCostDetail.setMaterialGroup(b.getMaterialGroup().trim());
                jigCostDetail.setMaterialGroupName(b.getMaterialGroupName().trim());
                jigCostDetail.setBudgetNumber(b.getBudgetNumber().trim());
                jigCostDetail.setSpecification(b.getSpecification().trim());
                jigCostDetail.setUnitPrice(b.getUnitPrice());
                jigCostDetail.setQuantity(b.getQuantity());
                jigCostDetail.setCurrency(b.getCurrency().trim());
                jigCostDetail.setPrAmount(b.getPrAmount());
                jigCostDetail.setPoAmount(b.getPoAmount());
                jigCostDetailList.add(jigCostDetail);
            }
            repository.saveAll(jigCostDetailList);
        }
    }
}
