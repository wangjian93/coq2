package com.ivo.coq.costCategory.oba.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.oba.entity.ObaCostDetail;
import com.ivo.coq.costCategory.oba.service.ObaCostDetailService;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.costCategory.oba.entity.ObaCost;
import com.ivo.coq.costCategory.oba.repository.ObaCostRepository;
import com.ivo.coq.costCategory.oba.service.ObaCostService;
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
public class ObaCostServiceImpl implements ObaCostService {

    private static Logger logger = LoggerFactory.getLogger(ObaCostServiceImpl.class);

    private ObaCostRepository repository;

    private StageService stageService;

    private ObaCostDetailService obaCostDetailService;

    @Autowired
    public ObaCostServiceImpl(ObaCostRepository repository, StageService stageService,
                              ObaCostDetailService obaCostDetailService) {
        this.repository = repository;
        this.stageService = stageService;
        this.obaCostDetailService = obaCostDetailService;
    }

    @Override
    public List<ObaCost> getObaCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<ObaCost> getObaCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public ObaCost getObaCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createObaCost(String project) {
        logger.info("创建 ObaCost >> " + project);
        repository.deleteAll(getObaCosts(project));
        List<Stage> stageList = stageService.getStage(project);
        List<ObaCost> obaCostList = new ArrayList<>();
        for(Stage stage : stageList) {
            ObaCost obaCost = new ObaCost(stage.getProject(), stage.getStage(), stage.getTime());
            // OBA费用只有EVT/DVT/PVT/MP阶段
            if(!StringUtils.equalsAnyIgnoreCase(stage.getStage(), StageEnum.EVT.getStage(), StageEnum.DVT.getStage(),
                    StageEnum.PVT.getStage(), StageEnum.MP.getStage())) {
                obaCost.setAmount(-1D);
            }
            obaCostList.add(obaCost);
        }
        repository.saveAll(obaCostList);
    }

    @Override
    public void computeObaCost(String project) {
        logger.info("计算OBA费用 >> " + project);
        List<ObaCost> obaCostList = getObaCosts(project);
        for(ObaCost obaCost : obaCostList) {
            if(obaCost.getAmount()!=null && obaCost.getAmount() == -1) continue;
            List<ObaCostDetail> obaCostDetailList = obaCostDetailService.getObaCostDetail(project, obaCost.getStage(), obaCost.getTime());
            Double amount = null;
            for(ObaCostDetail obaCostDetail : obaCostDetailList) {
                amount = DoubleUtil.sum(amount, obaCostDetail.getAmount());
            }
            obaCost.setAmount(amount);
        }
        repository.saveAll(obaCostList);
    }
}
