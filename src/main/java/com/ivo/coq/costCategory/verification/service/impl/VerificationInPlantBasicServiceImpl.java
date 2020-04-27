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
    public List<VerificationInPlantBasic> getVerificationInPlantBasics(int year) {
        return repository.findByYear(year);
    }

    @Override
    public Double getManPower(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.MAN_POWER);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getMonthlySalary(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.MONTHLY_SALARY);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getHumitureTotalPower(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.HUMITURE_TOTAL_POWER);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getElectricityBillPrice(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.ELECTRICITY_BILL_PRICE);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getVerificationQuantity(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.VERIFICATION_QUANTITY);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getHumitureVerificationQuantity(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.HUMITURE_VERIFICATION_QUANTITY);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getOrtVerificationQuantity(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.ORT_VERIFICATION_QUANTITY);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getMaterialChangeVerificationQuantity(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.MATERIAL_CHANGE_VERIFICATION_QUANTITY);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getOtherVerificationQuantity(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.OTHER_VERIFICATION_QUANTITY);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getNewProductVerificationQuantity(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.NEW_PRODUCT_VERIFICATION_QUANTITY);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getMaintainCost(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.MAINTAIN_COST);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getManPowerCostPer(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.MAN_POWE_Cost_Per);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public Double getMaintainCostPer(int year) {
        VerificationInPlantBasic basic =  repository.findFirstByYearAndName(year, VerificationInPlantBasic.MAINTAIN_COST_Per);
        if(basic == null) {
            return null;
        } else {
            return basic.getValue();
        }
    }

    @Override
    public void computeManPowerCostPer(int year) {
        log.info("计算" + year + "年的单片人力费用");
        //计算方式： DL人力数量*月薪*12个月/全年实验数量
        VerificationInPlantBasic basic = repository.findFirstByYearAndName(year, VerificationInPlantBasic.MAINTAIN_COST_Per);
        if(basic == null) {
            return;
        }

        Double manPower = getManPower(year);
        Double monthlySalary = getMonthlySalary(year);
        Double verificationQuantity = getVerificationQuantity(year);

        Double manPowerCostPer = DoubleUtil.multiply(manPower, 12D, monthlySalary);
        manPowerCostPer = DoubleUtil.divide(manPowerCostPer, verificationQuantity);
        basic.setValue(manPowerCostPer);
        repository.save(basic);
    }

    @Override
    public void computeMaintainCostPer(int year) {
        log.info("计算" + year + "年的单片维护费用");
        // 计算方式：全年机台维护费用/全年实验数量
        VerificationInPlantBasic basic = repository.findFirstByYearAndName(year, VerificationInPlantBasic.MAINTAIN_COST_Per);
        if(basic == null) {
            return;
        }

        Double maintainCost = getMaintainCost(year);
        Double verificationQuantity = getVerificationQuantity(year);

        Double maintainCostPer = DoubleUtil.divide(maintainCost, verificationQuantity);
        basic.setValue(maintainCostPer);
        repository.save(basic);
    }

    @Override
    public void computeHumitureTotalPower(int year) {
        log.info("计算" + year + "年温湿度机台总能耗");
        VerificationInPlantBasic basic = repository.findFirstByYearAndName(year, VerificationInPlantBasic.HUMITURE_TOTAL_POWER);
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
}
