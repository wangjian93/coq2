package com.ivo.coq.costCategory.travel.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.travel.entity.TravelCostDetail;
import com.ivo.coq.costCategory.travel.service.TravelCostDetailService;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.costCategory.travel.entity.TravelCost;
import com.ivo.coq.costCategory.travel.repository.TravelCostRepository;
import com.ivo.coq.costCategory.travel.service.TravelCostService;
import com.ivo.coq.project.service.StageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TravelCostServiceImpl implements TravelCostService {

    private static Logger logger = LoggerFactory.getLogger(TravelCostServiceImpl.class);

    private TravelCostRepository repository;

    private StageService stageService;

    private TravelCostDetailService travelCostDetailService;

    @Autowired
    public TravelCostServiceImpl(TravelCostRepository repository, StageService stageService,
                                 TravelCostDetailService travelCostDetailService) {
        this.repository = repository;
        this.stageService = stageService;
        this.travelCostDetailService = travelCostDetailService;
    }

    @Override
    public List<TravelCost> getTravelCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public TravelCost getTravelCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createTravelCost(String project) {
        logger.info("创建 TravelCost >> " + project);
        repository.deleteAll(getTravelCosts(project));
        List<Stage> stageList = stageService.getStage(project);
        List<TravelCost> travelCostList = new ArrayList<>();
        for(Stage stage : stageList) {
            TravelCost travelCost = new TravelCost(stage.getProject(), stage.getStage(), stage.getTime());
            // 差旅交际费用只有NPRB阶段
            if(!StringUtils.equalsAnyIgnoreCase(stage.getStage(), StageEnum.NPRB.getStage())) {
                travelCost.setPreventionAmount(-1D);
                travelCost.setInLossAmount(-1D);
                travelCost.setAmount(-1D);
            }
            travelCostList.add(travelCost);
        }
        repository.saveAll(travelCostList);
    }

    @Override
    public void computeTravelCost(String project) {
        logger.info("计算 TravelCost >> " + project);
        // 差旅费用全部算入NPRB阶段
        TravelCost travelCost = getTravelCost(project, StageEnum.NPRB.getStage(), null);
        // 预防
        List<TravelCostDetail> travelCostDetailList = travelCostDetailService.getTravelCostDetail(project, TravelCostDetail.TYPE_preventionCost);
        // 内损
        List<TravelCostDetail> travelCostDetailList_ = travelCostDetailService.getTravelCostDetail(project, TravelCostDetail.TYPE_inLossCost);

        Double preventionCost = null;
        Double inLossCost = null;
        for(TravelCostDetail travelCostDetail : travelCostDetailList) {
            preventionCost = DoubleUtil.sum(preventionCost,
                    travelCostDetail.getTransportationAmount(), travelCostDetail.getAccommodationAmount(),
                    travelCostDetail.getTelphoneAmount(), travelCostDetail.getMiscAmount(),
                    travelCostDetail.getIvomiscAmount(), travelCostDetail.getEntertainmentAmount());
        }
        for(TravelCostDetail travelCostDetail : travelCostDetailList_) {
            inLossCost = DoubleUtil.sum(inLossCost,
                    travelCostDetail.getTransportationAmount(), travelCostDetail.getAccommodationAmount(),
                    travelCostDetail.getTelphoneAmount(), travelCostDetail.getMiscAmount(),
                    travelCostDetail.getIvomiscAmount(), travelCostDetail.getEntertainmentAmount());
        }
        travelCost.setPreventionAmount(preventionCost);
        travelCost.setInLossAmount(inLossCost);
        travelCost.setAmount(DoubleUtil.sum(preventionCost, inLossCost));
        repository.save(travelCost);
    }
}
