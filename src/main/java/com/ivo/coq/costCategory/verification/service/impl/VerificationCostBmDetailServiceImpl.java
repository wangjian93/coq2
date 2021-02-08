package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.coq.costCategory.verification.entity.VerificationCostBmDetail;
import com.ivo.coq.costCategory.verification.repository.VerificationCostBmDetailRepository;
import com.ivo.coq.costCategory.verification.service.VerificationCostBmDetailService;
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
public class VerificationCostBmDetailServiceImpl implements VerificationCostBmDetailService {

    private VerificationCostBmDetailRepository repository;

    private StageService stageService;

    private MilestoneService milestoneService;

    private BmService bmService;

    @Autowired
    public VerificationCostBmDetailServiceImpl(VerificationCostBmDetailRepository repository,
                                               StageService stageService,
                                               MilestoneService milestoneService,
                                               BmService bmService) {
        this.repository = repository;
        this.stageService = stageService;
        this.milestoneService = milestoneService;
        this.bmService = bmService;
    }

    @Override
    public List<VerificationCostBmDetail> getVerificationCostBmDetail(String project) {
        return repository.findByProject(project);
    }

    @Override
    public List<VerificationCostBmDetail> getVerificationCostBmDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void syncVerificationCostBmDetail(String project) {
        log.info("同步获取机种的BM验证费用 " + project);
        repository.deleteAll(getVerificationCostBmDetail(project));
        List<String> stages = stageService.getStageNoTime(project);
        if (stages == null) return;
        for (String stage : stages) {
            Date[] dates = milestoneService.getStageAndEndDate(project, stage);
            if (dates == null || dates.length < 2) continue;
            Date fromDate = dates[0];
            Date toDate = dates[1];
            if (fromDate == null || toDate == null) continue;
            List<BmModel> bmModelList = bmService.getBmVerification(project, fromDate, toDate);
            if (bmModelList == null) continue;
            List<VerificationCostBmDetail> verificationCostBmDetailList = new ArrayList<>();
            for (BmModel b : bmModelList) {
                // BM验证费用先全部默认算到机种阶段的次数1中
                Integer time = 1;
                if (StringUtils.equalsAnyIgnoreCase(stage, StageEnum.NPRB.getStage(), StageEnum.Design.getStage()))
                    time = null;
                VerificationCostBmDetail bmDetail = new VerificationCostBmDetail(project, stage, time);
                bmDetail.setPr(b.getPr().trim());
                bmDetail.setPrDrafter(b.getPrDrafter().trim());
                bmDetail.setPrDrafterDate(b.getPrDrafterDate());
                bmDetail.setPrItem(b.getPrItem().trim());
                bmDetail.setBudgetType(b.getBudgetType().trim());
                bmDetail.setMaterialGroup(b.getMaterialGroup().trim());
                bmDetail.setMaterialGroupName(b.getMaterialGroupName().trim());
                bmDetail.setBudgetNumber(b.getBudgetNumber().trim());
                bmDetail.setSpecification(b.getSpecification().trim());
                bmDetail.setUnitPrice(b.getUnitPrice());
                bmDetail.setQuantity(b.getQuantity());
                bmDetail.setCurrency(b.getCurrency().trim());
                bmDetail.setPrAmount(b.getPrAmount());
                bmDetail.setPoAmount(b.getPoAmount());
                verificationCostBmDetailList.add(bmDetail);
            }
            repository.saveAll(verificationCostBmDetailList);
        }
    }
}
