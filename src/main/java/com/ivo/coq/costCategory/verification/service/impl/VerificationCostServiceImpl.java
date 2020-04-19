package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.verification.entity.VerificationCostBmDetail;
import com.ivo.coq.costCategory.verification.entity.VerificationCostPlantDetail;
import com.ivo.coq.costCategory.verification.service.VerificationCostBmDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationCostPlantDetailService;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.costCategory.verification.entity.VerificationCost;
import com.ivo.coq.costCategory.verification.repository.VerificationCostRepository;
import com.ivo.coq.costCategory.verification.service.VerificationCostService;
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
public class VerificationCostServiceImpl implements VerificationCostService {

    private VerificationCostRepository repository;

    private StageService stageService;

    private VerificationCostBmDetailService bmDetailService;

    private VerificationCostPlantDetailService plantDetailService;

    @Autowired
    public VerificationCostServiceImpl(VerificationCostRepository repository, StageService stageService,
                                       VerificationCostBmDetailService bmDetailService,
                                       VerificationCostPlantDetailService plantDetailService) {
        this.repository = repository;
        this.stageService = stageService;
        this.bmDetailService = bmDetailService;
        this.plantDetailService = plantDetailService;
    }

    @Override
    public List<VerificationCost> getVerificationCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<VerificationCost> getVerificationCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public VerificationCost getVerificationCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void crateVerificationCost(String project) {
        log.info("创建机种的验证费用 >> " + project);
        repository.deleteAll(getVerificationCosts(project));
        List<Stage> stageList = stageService.getStage(project);
        List<VerificationCost> verificationCostList = new ArrayList<>();
        for(Stage stage : stageList) {
            VerificationCost verificationCost = new VerificationCost(stage.getProject(), stage.getStage(),
                    stage.getTime());

            // 验证费用只有EVT/DVT/PVT阶段
            if(!StringUtils.equalsAnyIgnoreCase(stage.getStage(), StageEnum.EVT.getStage(), StageEnum.DVT.getStage(),
                    StageEnum.PVT.getStage())) {
                verificationCost.setBmVerificationAmount(-1D);
                verificationCost.setInPlantVerificationAmount(-1D);
                verificationCost.setAmount(-1D);
            }
            verificationCostList.add(verificationCost);
        }
        repository.saveAll(verificationCostList);
    }

    @Override
    public void computeVerificationCost(String project) {
        log.info("计算机种的验证费用 >> " + project);
        List<VerificationCost> verificationCostList = getVerificationCosts(project);
        for(VerificationCost verificationCost : verificationCostList) {
            // BM部分
            Double bmAmount = null;
            if(verificationCost.getBmVerificationAmount()==null || verificationCost.getBmVerificationAmount()!=-1) {
                List<VerificationCostBmDetail> bmDetailList = bmDetailService.getVerificationCostBmDetail(verificationCost.getProject(),
                        verificationCost.getStage(), verificationCost.getTime());
                for(VerificationCostBmDetail bmDetail : bmDetailList) {
                    bmAmount = DoubleUtil.sum(bmAmount, bmDetail.getPoAmount());
                }
                verificationCost.setBmVerificationAmount(bmAmount);
            }

            // 厂内验证部分
            Double plantAmount = null;
            if(verificationCost.getInPlantVerificationAmount()==null || verificationCost.getInPlantVerificationAmount()!=-1) {
                List<VerificationCostPlantDetail> plantDetailList = plantDetailService.getVerificationCostPlantDetail(verificationCost.getProject(),
                        verificationCost.getStage(), verificationCost.getTime());
                for(VerificationCostPlantDetail plantDetail : plantDetailList) {
                    plantAmount = DoubleUtil.sum(plantAmount, plantDetail.getAmount());
                }
                verificationCost.setInPlantVerificationAmount(plantAmount);
            }

            if(verificationCost.getAmount()==null || verificationCost.getAmount()!=-1) {
                verificationCost.setAmount(DoubleUtil.sum(bmAmount, plantAmount));
            }
        }
        repository.saveAll(verificationCostList);
    }
}
