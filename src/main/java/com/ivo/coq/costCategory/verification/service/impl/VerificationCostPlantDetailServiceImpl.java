package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.coq.costCategory.verification.entity.VerificationCostPlantDetail;
import com.ivo.coq.costCategory.verification.repository.VerificationCostPlantDetailRepository;
import com.ivo.coq.costCategory.verification.service.VerificationCostPlantDetailService;
import com.ivo.rest.qms.QmsService;
import com.ivo.rest.qms.entity.QmsVerification;
import lombok.extern.slf4j.Slf4j;
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
public class VerificationCostPlantDetailServiceImpl implements VerificationCostPlantDetailService {

    private VerificationCostPlantDetailRepository repository;

    private QmsService qmsService;

    @Autowired
    public VerificationCostPlantDetailServiceImpl(VerificationCostPlantDetailRepository repository, QmsService qmsService) {
        this.repository = repository;
        this.qmsService = qmsService;
    }

    @Override
    public List<VerificationCostPlantDetail> getVerificationCostPlantDetail(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<VerificationCostPlantDetail> getVerificationCostPlantDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }

    @Override
    public void syncVerificationCostPlantDetail(String project) {
        log.info("从QMS同步机种的厂内验证信息 " + project);
        repository.deleteAll(getVerificationCostPlantDetail(project));
        List<QmsVerification> qmsVerificationList = qmsService.getQmsVerification(project);
        if(qmsVerificationList == null) return;
        List<VerificationCostPlantDetail> plantDetailList = new ArrayList<>();
        for(QmsVerification qmsVerification : qmsVerificationList) {
            // 阶段QMS中的格式为 EVT-1
            String stage = null;
            Integer time = null;
            if(qmsVerification.getVerificationStage() != null) {
                String stage_ = qmsVerification.getVerificationStage().trim().toUpperCase();
                String[] string_s = stage_.split("-");
                if(string_s.length>0) {
                    stage = string_s[0];
                }
                if(string_s.length>1) {
                    time = Integer.parseInt(string_s[1]);
                }
            }

            VerificationCostPlantDetail plantDetail = new VerificationCostPlantDetail();
            plantDetail.setProject(project);
            plantDetail.setStage(stage);
            plantDetail.setTime(time);
            plantDetail.setVerificationType(qmsVerification.getVerificationType());
            plantDetail.setVerificationSubject(qmsVerification.getVerificationSubject());
            plantDetail.setVerificationCondition(qmsVerification.getVerificationCondition());
            plantDetail.setVerificationQuantity(qmsVerification.getVerificationQuantity());
            plantDetail.setVerificationUnit(qmsVerification.getVerificationUnit());
            plantDetail.setVerificationStage(qmsVerification.getVerificationStage());
            plantDetailList.add(plantDetail);
        }
        repository.saveAll(plantDetailList);
    }

    @Override
    public void computeVerificationCostPlantDetail(String project) {
        log.info("计算机种的厂内验证费用 " + project);


    }
}
