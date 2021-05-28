package com.ivo.coq.costCategory.jig.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.jig.entity.JigCostDetail;
import com.ivo.coq.costCategory.jig.service.JigCostDetailService;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.costCategory.jig.entity.JigCost;
import com.ivo.coq.costCategory.jig.repository.JigCostRepository;
import com.ivo.coq.costCategory.jig.service.JigCostService;
import com.ivo.coq.project.service.StageService;
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
public class JigCostServiceImpl implements JigCostService {

    private static Logger logger = LoggerFactory.getLogger(JigCostServiceImpl.class);

    private JigCostRepository repository;

    private StageService stageService;

    private JigCostDetailService jigCostDetailService;


    @Autowired
    public JigCostServiceImpl(JigCostRepository repository, StageService stageService,
                              JigCostDetailService jigCostDetailService) {
        this.repository = repository;
        this.stageService = stageService;
        this.jigCostDetailService = jigCostDetailService;
    }

    @Override
    public List<JigCost> getJigCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public JigCost getJigCost(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public List<JigCost> getJigCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public void createJigCost(String project) {
        logger.info("创建 JigCost >> " + project);
        repository.deleteAll(getJigCosts(project));
        List<Stage> stageList = stageService.getStage(project);
        List<JigCost> jigCostList = new ArrayList<>();
        for(Stage projectStage : stageList) {
            JigCost jigCost = new JigCost(projectStage.getProject(), projectStage.getStage(), projectStage.getTime());
            String stage = jigCost.getStage();
            // 治工具费用只有DESIGN/EVT/DVT/PVT阶段
            if(!stage.equals("DESIGN") && !stage.equals("EVT") && !stage.equals("DVT") && !stage.equals("PVT")) {
                jigCost.setInLossCost(-1D);
                jigCost.setPreventionCost(-1D);
                jigCost.setAmount(-1D);
            }
            jigCostList.add(jigCost);
        }
        repository.saveAll(jigCostList);
    }

    @Override
    public void computeJigCost(String project) {
        logger.info("计算 JigCost >> " + project);
        List<JigCost> jigCostList = getJigCosts(project);
        for(JigCost jigCost : jigCostList) {
            if(jigCost.getAmount()!=null && jigCost.getAmount()==-1) continue;
            List<JigCostDetail> jigCostDetailList = jigCostDetailService.getJigCostDetail(jigCost.getProject(),
                    jigCost.getStage(), jigCost.getTime());
            Double amount = null;
            Double preventionCost = null;
            Double inLossCost = null;
            for(JigCostDetail jigCostDetail : jigCostDetailList) {
                if(StringUtils.equals(jigCostDetail.getType(), "预防")) {
                    preventionCost = DoubleUtil.sum(preventionCost, jigCostDetail.getPoAmount());
                } else {
                    inLossCost = DoubleUtil.sum(inLossCost, jigCostDetail.getPoAmount());
                }
            }
            amount = DoubleUtil.sum(preventionCost, inLossCost);
            jigCost.setPreventionCost(preventionCost);
            jigCost.setInLossCost(inLossCost);
            jigCost.setAmount(amount);
        }
        repository.saveAll(jigCostList);
    }
}
