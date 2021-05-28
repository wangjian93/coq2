package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.verification.entity.VerificationInPlantBasic;
import com.ivo.coq.costCategory.verification.entity.VerificationMachine;
import com.ivo.coq.costCategory.verification.repository.VerificationInPlantBasicRepository;
import com.ivo.coq.costCategory.verification.service.VerificationInPlantBasicService;
import com.ivo.coq.costCategory.verification.service.VerificationMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class VerificationInPlantBasicServiceImpl implements VerificationInPlantBasicService {

    private VerificationInPlantBasicRepository repository;

    private VerificationMachineService verificationMachineService;

    @Autowired
    public VerificationInPlantBasicServiceImpl(VerificationInPlantBasicRepository repository,
                                               VerificationMachineService verificationMachineService) {
        this.repository = repository;
        this.verificationMachineService = verificationMachineService;
    }

    @Override
    public List<VerificationInPlantBasic> getVerificationInPlantBasics() {
        return repository.findByValidFlagIsTrue();
    }

    @Override
    public Double getBasicData(String name) {
        VerificationInPlantBasic verificationInPlantBasic = repository.findFirstByNameAndValidFlagIsTrue(name);
        return verificationInPlantBasic == null ? null : verificationInPlantBasic.getValue();
    }

    @Override
    public void computeManPowerCostPer() {
        log.info("计算单片人力费用");
        //计算方式： DL人力数量*月薪*12个月/全年实验数量
        VerificationInPlantBasic basic = repository.findFirstByNameAndValidFlagIsTrue(VerificationInPlantBasic.MAN_POWE_Cost_Per);
        if(basic == null) {
            return;
        }

        Double manPower = getBasicData(VerificationInPlantBasic.MAN_POWER);
        Double monthlySalary = getBasicData(VerificationInPlantBasic.MONTHLY_SALARY);
        Double verificationQuantity = getBasicData(VerificationInPlantBasic.VERIFICATION_QUANTITY);

        Double manPowerCostPer = DoubleUtil.multiply(manPower, 12D, monthlySalary);
        manPowerCostPer = DoubleUtil.divide(manPowerCostPer, verificationQuantity);
        basic.setValue(manPowerCostPer);
        repository.save(basic);
    }

    @Override
    public void computeMaintainCostPer() {
        log.info("计算单片维护费用");
        // 计算方式：全年机台维护费用/全年实验数量
        VerificationInPlantBasic basic = repository.findFirstByNameAndValidFlagIsTrue(VerificationInPlantBasic.MAINTAIN_COST_Per);
        if(basic == null) {
            return;
        }

        Double maintainCost = getBasicData(VerificationInPlantBasic.MAINTAIN_COST);
        Double verificationQuantity = getBasicData(VerificationInPlantBasic.VERIFICATION_QUANTITY);

        Double maintainCostPer = DoubleUtil.divide(maintainCost, verificationQuantity);
        basic.setValue(maintainCostPer);
        repository.save(basic);
    }

    @Override
    public void computeHumitureTotalPower() {
        log.info("计算温湿度机台总能耗");
        VerificationInPlantBasic basic = repository.findFirstByNameAndValidFlagIsTrue(VerificationInPlantBasic.HUMITURE_TOTAL_POWER);
        if(basic == null) {
            return;
        }

        List<VerificationMachine> verificationMachineList = verificationMachineService.getHumitureVerificationMachines();
        if(verificationMachineList == null) {
            return;
        }

        Double humitureTotalPower = null;
        for(VerificationMachine verificationMachine : verificationMachineList) {
            humitureTotalPower = DoubleUtil.sum(humitureTotalPower, verificationMachine.getPower());
        }
        basic.setValue(humitureTotalPower);
        repository.save(basic);
    }

    @Override
    public void computeHumitureCostPer() {
        log.info("计算单片温湿度机台能耗费用");
        // 1.温湿度类  温湿度类机台总能耗*24小时*365天/1000*电费/全年温湿度类实验数量
        // 电费 = 电费价格 * 度数
        // 1度电 = 1KW*H (千瓦时)
        VerificationInPlantBasic basic = repository.findFirstByNameAndValidFlagIsTrue(VerificationInPlantBasic.HUMITURE_COST_PER);
        Double humitureTotalPower = getBasicData(VerificationInPlantBasic.HUMITURE_TOTAL_POWER);
        Double electricityBillPrice = getBasicData(VerificationInPlantBasic.ELECTRICITY_BILL_PRICE);
        Double humitureVerificationQuantity = getBasicData(VerificationInPlantBasic.HUMITURE_VERIFICATION_QUANTITY);
        Double humiture_costPer = DoubleUtil.divide(humitureTotalPower, 1000D);
        humiture_costPer = DoubleUtil.multiply(humiture_costPer, 24D, 365D);
        humiture_costPer = DoubleUtil.multiply(humiture_costPer, electricityBillPrice);
        humiture_costPer = DoubleUtil.divide(humiture_costPer, humitureVerificationQuantity);
        basic.setValue(humiture_costPer);
        repository.save(basic);
    }

    @Override
    public void save(List<VerificationInPlantBasic> list) {
        List<VerificationInPlantBasic> oldList = getVerificationInPlantBasics();
        for(VerificationInPlantBasic basic : oldList) {
            basic.setValidFlag(false);
            basic.setExpireDate(new Date());
        }
        repository.saveAll(oldList);
        repository.saveAll(list);

        //
        VerificationInPlantBasic HUMITURE_COST_PER = new VerificationInPlantBasic();
        HUMITURE_COST_PER.setName(VerificationInPlantBasic.HUMITURE_COST_PER);
        HUMITURE_COST_PER.setLabel("温湿度机台能耗-单片");
        HUMITURE_COST_PER.setMemo("机台能耗*24小时*365天/1000*电费/全年温湿度类实验数量");
        HUMITURE_COST_PER.setValue(0D);
        HUMITURE_COST_PER.setUnit("RMB");
        HUMITURE_COST_PER.setVersion(list.get(0).getVersion());
        HUMITURE_COST_PER.setValidFlag(true);
        HUMITURE_COST_PER.setEffectDate(list.get(0).getEffectDate());
        repository.save(HUMITURE_COST_PER);

        VerificationInPlantBasic MAN_POWE_Cost_Per = new VerificationInPlantBasic();
        MAN_POWE_Cost_Per.setName(VerificationInPlantBasic.MAN_POWE_Cost_Per);
        MAN_POWE_Cost_Per.setLabel("人力费用-单片");
        MAN_POWE_Cost_Per.setMemo("DL数量*月薪*12个月/全年实验数量");
        MAN_POWE_Cost_Per.setValue(0D);
        MAN_POWE_Cost_Per.setUnit("RMB");
        MAN_POWE_Cost_Per.setVersion(list.get(0).getVersion());
        MAN_POWE_Cost_Per.setValidFlag(true);
        MAN_POWE_Cost_Per.setEffectDate(list.get(0).getEffectDate());
        repository.save(MAN_POWE_Cost_Per);

        VerificationInPlantBasic MAINTAIN_COST_Per = new VerificationInPlantBasic();
        MAINTAIN_COST_Per.setName(VerificationInPlantBasic.MAINTAIN_COST_Per);
        MAINTAIN_COST_Per.setLabel("维护费用-单片");
        MAINTAIN_COST_Per.setMemo("全年机台维护费用/全年实验数量");
        MAINTAIN_COST_Per.setValue(0D);
        MAINTAIN_COST_Per.setUnit("RMB");
        MAINTAIN_COST_Per.setVersion(list.get(0).getVersion());
        MAINTAIN_COST_Per.setValidFlag(true);
        MAINTAIN_COST_Per.setEffectDate(list.get(0).getEffectDate());
        repository.save(MAINTAIN_COST_Per);

        computeHumitureTotalPower();
        computeHumitureCostPer();
        computeManPowerCostPer();
        computeMaintainCostPer();
    }
}
