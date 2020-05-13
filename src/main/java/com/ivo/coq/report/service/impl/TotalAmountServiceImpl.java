package com.ivo.coq.report.service.impl;

import com.ivo.common.enums.PlantEnum;
import com.ivo.coq.report.entity.TotalAmount;
import com.ivo.coq.report.repository.TotalAmountRepository;
import com.ivo.coq.report.service.TotalAmountService;
import com.ivo.rest.oracle.OracleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class TotalAmountServiceImpl implements TotalAmountService {

    private TotalAmountRepository totalAmountRepository;

    private OracleService oracleService;

    public TotalAmountServiceImpl(TotalAmountRepository totalAmountRepository,
                                  OracleService oracleService) {
        this.totalAmountRepository = totalAmountRepository;
        this.oracleService = oracleService;
    }

    @Override
    public void syncTotalAmount() {
        log.info("同步各厂的总制造成本");
        totalAmountRepository.deleteAll();
        List<TotalAmount> list = oracleService.getTotalAmount();
        totalAmountRepository.saveAll(list);
    }

    @Override
    public TotalAmount getTotalAmount(String plant, String mon) {
        if(StringUtils.equalsIgnoreCase(plant, PlantEnum.Array.getPlant())) {
            plant = "ARY";
        } else if(StringUtils.equalsIgnoreCase(plant, PlantEnum.Cell.getPlant())) {
            plant = "CEL";
        } else if(StringUtils.equalsIgnoreCase(plant, PlantEnum.Lcm1.getPlant())) {
            plant = "LCM1";
        } else if(StringUtils.equalsIgnoreCase(plant, PlantEnum.Lcm2.getPlant())) {
            plant = "LCM2";
        }
        return totalAmountRepository.findFirstByPlantAndMon(plant, mon);
    }
}
