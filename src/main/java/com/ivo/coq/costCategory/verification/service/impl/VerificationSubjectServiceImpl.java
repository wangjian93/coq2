package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.verification.entity.VerificationInPlantBasic;
import com.ivo.coq.costCategory.verification.entity.VerificationMachine;
import com.ivo.coq.costCategory.verification.entity.VerificationSubject;
import com.ivo.coq.costCategory.verification.repository.VerificationSubjectRepository;
import com.ivo.coq.costCategory.verification.service.VerificationInPlantBasicService;
import com.ivo.coq.costCategory.verification.service.VerificationMachineService;
import com.ivo.coq.costCategory.verification.service.VerificationSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Slf4j
@Service
public class VerificationSubjectServiceImpl implements VerificationSubjectService {

    private VerificationSubjectRepository repository;

    private VerificationInPlantBasicService verificationInPlantBasicService;

    private VerificationMachineService verificationMachineService;

    @Autowired
    public VerificationSubjectServiceImpl(VerificationSubjectRepository repository,
                                          VerificationInPlantBasicService verificationInPlantBasicService,
                                          VerificationMachineService verificationMachineService) {
        this.repository = repository;
        this.verificationInPlantBasicService = verificationInPlantBasicService;
        this.verificationMachineService = verificationMachineService;
    }

    @Override
    public VerificationSubject getVerificationSubject(String subjectName) {
        return repository.findFirstBySubjectName(subjectName);
    }

    @Override
    public List<VerificationSubject> getVerificationSubjects() {
        return repository.findAll();
    }

    @Override
    public List<VerificationSubject> getHumitureVerificationSubjects() {
        return repository.findByHumitureType(VerificationSubject.Humiture_Type_Yes);
    }

    @Override
    public List<VerificationSubject> getNoHumitureVerificationSubjects() {
        return repository.findByHumitureType(VerificationSubject.Humiture_Type_No);
    }

    @Override
    public void computeElectricityBillPer() {
        log.info("计算验证项目的单片电费");
        // 1.温湿度类
        Double humiture_costPer = verificationInPlantBasicService.getBasicData(VerificationInPlantBasic.HUMITURE_COST_PER);
        List<VerificationSubject> humitureSubjectList = getHumitureVerificationSubjects();
        for(VerificationSubject humitureSubject : humitureSubjectList) {
            humitureSubject.setElectricityBillPer(humiture_costPer);
        }
        repository.saveAll(humitureSubjectList);

        // 2.非温湿度类  机台能耗/1000*项目的验证时间（小时）* 电费
        Double electricityBillPrice = verificationInPlantBasicService.getBasicData(VerificationInPlantBasic.ELECTRICITY_BILL_PRICE);
        List<VerificationSubject> noHumitureSubjectList = getNoHumitureVerificationSubjects();
        for(VerificationSubject noHumitureSubject : noHumitureSubjectList) {
            Double verificationTime = noHumitureSubject.getVerificationTime();
            VerificationMachine verificationMachine = verificationMachineService
                    .getVerificationMachine(noHumitureSubject.getMachineName());
            if(verificationMachine == null) {
                continue;
            }
            Double noHumiture_costPer = DoubleUtil.divide(verificationMachine.getPower(), 1000D);
            noHumiture_costPer = DoubleUtil.multiply(noHumiture_costPer, verificationTime);
            noHumiture_costPer = DoubleUtil.multiply(noHumiture_costPer, electricityBillPrice);
            noHumitureSubject.setElectricityBillPer(noHumiture_costPer);
        }
        repository.saveAll(noHumitureSubjectList);
    }
}
