package com.ivo.coq.report.service.impl;

import com.ivo.common.enums.PlantEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.report.entity.InLossAmount;
import com.ivo.coq.report.entity.InLossAmountDetailArrayCell;
import com.ivo.coq.report.entity.InLossAmountDetailLcm;
import com.ivo.coq.report.entity.TotalAmount;
import com.ivo.coq.report.repository.InLossAmountDetailArrayCellRepository;
import com.ivo.coq.report.repository.InLossAmountDetailLcmRepository;
import com.ivo.coq.report.repository.InLossAmountRepository;
import com.ivo.coq.report.service.InLossAmountService;
import com.ivo.coq.report.service.TotalAmountService;
import com.ivo.rest.oracle.OracleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class InLossAmountServiceImpl implements InLossAmountService {

    private InLossAmountRepository inLossAmountRepository;
    private InLossAmountDetailArrayCellRepository inLossAmountDetailArrayCellRepository;
    private InLossAmountDetailLcmRepository inLossAmountDetailLcmRepository;
    private OracleService oracleService;

    private TotalAmountService totalAmountService;

    @Autowired
    public InLossAmountServiceImpl(InLossAmountRepository inLossAmountRepository,
                                   InLossAmountDetailArrayCellRepository inLossAmountDetailArrayCellRepository,
                                   OracleService oracleService,
                                   InLossAmountDetailLcmRepository inLossAmountDetailLcmRepository,
                                   TotalAmountService totalAmountService) {
        this.inLossAmountRepository = inLossAmountRepository;
        this.inLossAmountDetailArrayCellRepository = inLossAmountDetailArrayCellRepository;
        this.oracleService = oracleService;
        this.inLossAmountDetailLcmRepository = inLossAmountDetailLcmRepository;
        this.totalAmountService = totalAmountService;
    }

    @Override
    public void syncInLossAmount() {
        log.info("同步各厂的内损成本");
        inLossAmountRepository.deleteAll();
        // Array/cell
        List<InLossAmount> list1 = oracleService.getInLossAmountArrayCell();
        inLossAmountRepository.saveAll(list1);
        // LCM
        List<InLossAmount> list2 = oracleService.getInLossAmountArrayLcm();
        inLossAmountRepository.saveAll(list2);
    }

    @Override
    public void syncInLossAmountDetailArrayCell() {
        log.info("同步Array/CELL的内损成本明细");
        inLossAmountDetailArrayCellRepository.deleteAll();
        List<InLossAmountDetailArrayCell> list = oracleService.getInLossAmountDetailArrayCell();
        inLossAmountDetailArrayCellRepository.saveAll(list);
    }

    @Override
    public void syncInLossAmountDetailLcm() {
        log.info("同步LCM的内损成本明细");
        inLossAmountDetailLcmRepository.deleteAll();
        List<InLossAmountDetailLcm> list = oracleService.getInLossAmountDetailLcm();
        inLossAmountDetailLcmRepository.saveAll(list);
    }

    @Override
    public List<InLossAmount> getInLossAmount(String FAB_ID, Date fromDate, Date toDate) {
        if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Array.getPlant())) {
            FAB_ID = "ARY";
        } else if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Cell.getPlant())) {
            FAB_ID = "CEL";
        } else if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Lcm1.getPlant())) {
            FAB_ID = "1000";
        } else if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Lcm2.getPlant())) {
            FAB_ID = "3000";
        }
        return inLossAmountRepository.findByPlantAndFabDateBetween(FAB_ID, fromDate, toDate);
    }

    @Override
    public List<InLossAmountDetailArrayCell> getInLossAmountDetailArrayCell(String FAB_ID, Date FAB_DATE) {
        if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Array.getPlant())) {
            FAB_ID = "ARY";
        } else if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Cell.getPlant())) {
            FAB_ID = "CEL";
        } else if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Lcm1.getPlant())) {
            FAB_ID = "1000";
        } else if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Lcm2.getPlant())) {
            FAB_ID = "3000";
        }
        return inLossAmountDetailArrayCellRepository.findByFabIdAndFabDate(FAB_ID, FAB_DATE);
    }

    @Override
    public List<InLossAmountDetailLcm> getInLossAmountDetailLcm(String FAB_ID, Date FAB_DATE) {
        if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Array.getPlant())) {
            FAB_ID = "ARY";
        } else if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Cell.getPlant())) {
            FAB_ID = "CEL";
        } else if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Lcm1.getPlant())) {
            FAB_ID = "1000";
        } else if(StringUtils.equalsIgnoreCase(FAB_ID, PlantEnum.Lcm2.getPlant())) {
            FAB_ID = "3000";
        }
        return inLossAmountDetailLcmRepository.findByPlantAndFabDate(FAB_ID, FAB_DATE);
    }

    @Override
    public List<Map> getInLossAmountRatio(String FAB_ID, Date fromDate, Date toDate) {
        List<InLossAmount> inLossAmountList = getInLossAmount(FAB_ID, fromDate, toDate);
        List<Map> list = new ArrayList<>();
        if(inLossAmountList == null || inLossAmountList.size()==0) return list;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for(InLossAmount inLossAmount : inLossAmountList) {
            Map<String, Object> map = new HashMap<>();
            map.put("FAB_DATE", sdf.format(inLossAmount.getFabDate()));
            map.put("PLANT", inLossAmount.getPlant());
            map.put("AMOUNT", inLossAmount.getAmount());
            TotalAmount totalAmount = totalAmountService.getTotalAmount(FAB_ID, sdf.format(inLossAmount.getFabDate()));
            if(totalAmount == null) {
                map.put("TOTAL_AMOUNT", null);
                map.put("RATIO", null);
            } else {
                map.put("TOTAL_AMOUNT", totalAmount.getAmount());
                if(inLossAmount.getAmount() == null) {
                    map.put("RATIO", null);
                } else {
                    Double ratio = DoubleUtil.divide(inLossAmount.getAmount()*100, totalAmount.getAmount());
                    map.put("RATIO", ratio);

                }
            }
            list.add(map);
        }
        return list;
    }
}
