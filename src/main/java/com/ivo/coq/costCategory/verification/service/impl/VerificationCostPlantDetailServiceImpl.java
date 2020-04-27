package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.verification.entity.VerificationCostPlantDetail;
import com.ivo.coq.costCategory.verification.entity.VerificationSubject;
import com.ivo.coq.costCategory.verification.repository.VerificationCostPlantDetailRepository;
import com.ivo.coq.costCategory.verification.service.VerificationCostPlantDetailService;
import com.ivo.coq.costCategory.verification.service.VerificationInPlantBasicService;
import com.ivo.coq.costCategory.verification.service.VerificationSubjectService;
import com.ivo.rest.qms.QmsService;
import com.ivo.rest.qms.entity.QmsVerification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
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

    private VerificationInPlantBasicService verificationInPlantBasicService;
    private VerificationSubjectService verificationSubjectService;

    @Autowired
    public VerificationCostPlantDetailServiceImpl(VerificationCostPlantDetailRepository repository, QmsService qmsService,
                                                  VerificationInPlantBasicService verificationInPlantBasicService,
                                                  VerificationSubjectService verificationSubjectService) {
        this.repository = repository;
        this.qmsService = qmsService;
        this.verificationInPlantBasicService = verificationInPlantBasicService;
        this.verificationSubjectService = verificationSubjectService;
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
            // 如果time在QMS中没维护默认到1
            if(time == null) {
                time = 1;
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
        List<VerificationCostPlantDetail> verificationInPlantDetailList = getVerificationCostPlantDetail(project);
        int year = 2019;
        Double manPowerCostPer = verificationInPlantBasicService.getManPowerCostPer(year);
        Double maintainCostPer =  verificationInPlantBasicService.getMaintainCostPer(year);
        verificationInPlantDetailList.forEach(verificationInPlantDetail -> {

            // 计算单片人力费用
            verificationInPlantDetail.setManPowerAmountPer(manPowerCostPer);

            // 计算单片维护费用
            verificationInPlantDetail.setMaintainAmountPer(maintainCostPer);

            // 计算单片电费
            VerificationSubject verificationSubject = verificationSubjectService.getVerificationSubject(
                    verificationInPlantDetail.getVerificationSubject());
            if(verificationSubject != null) {
                verificationInPlantDetail.setElectricityBillAmountPer(verificationSubject.getElectricityBillPer());
            }

            Double quantity = verificationInPlantDetail.getVerificationQuantity();

            // 计算人力费用
            verificationInPlantDetail.setManPowerAmount(
                    DoubleUtil.multiply(quantity, verificationInPlantDetail.getManPowerAmountPer()));

            // 计算维护费用
            verificationInPlantDetail.setMaintainAmount(
                    DoubleUtil.multiply(quantity, verificationInPlantDetail.getMaintainAmountPer()));

            // 计算电费
            verificationInPlantDetail.setElectricityBillAmount(
                    DoubleUtil.multiply(quantity, verificationInPlantDetail.getElectricityBillAmountPer())
            );

            // 计算产内验证费用
            verificationInPlantDetail.setAmount(
                    DoubleUtil.sum(verificationInPlantDetail.getManPowerAmount(), verificationInPlantDetail.getMaintainAmount(),
                            verificationInPlantDetail.getElectricityBillAmount())
            );
        });

        repository.saveAll(verificationInPlantDetailList);

    }
}
