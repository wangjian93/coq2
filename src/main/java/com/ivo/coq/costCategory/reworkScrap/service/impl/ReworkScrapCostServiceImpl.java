package com.ivo.coq.costCategory.reworkScrap.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostArray;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostCell;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostLcm;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostDetailService;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCost;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostRepository;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostService;
import com.ivo.coq.project.service.StageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class ReworkScrapCostServiceImpl implements ReworkScrapCostService {

    private ReworkScrapCostRepository repository;

    private ReworkScrapCostDetailService reworkScrapCostDetailService;

    private StageService stageService;

    @Autowired
    public ReworkScrapCostServiceImpl(ReworkScrapCostRepository repository,
                                      ReworkScrapCostDetailService reworkScrapCostDetailService,
                                      StageService stageService) {
        this.repository = repository;
        this.reworkScrapCostDetailService = reworkScrapCostDetailService;
        this.stageService = stageService;
    }

    @Override
    public List<ReworkScrapCost> getReworkScrapCosts(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<ReworkScrapCost> getReworkScrapCosts(String project, String stage) {
        return repository.findByProjectAndStageOrderById(project, stage);
    }

    @Override
    public ReworkScrapCost getReworkScrapCost(String project, String stage, Integer time) {
        return repository.findFirstByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void createReworkScrapCost(String project) {
        log.info("创建 ReworkScrapCost >> " + project);
        repository.deleteAll(getReworkScrapCosts(project));
        List<Stage> stageList = stageService.getStage(project);
        List<ReworkScrapCost> reworkScrapCostList = new ArrayList<>();
        for(Stage stage : stageList) {
            ReworkScrapCost reworkScrapCost = new ReworkScrapCost(stage.getProject(), stage.getStage(), stage.getTime());
            // 重工报废费用只有EVT/DVT/PVT/MP阶段
            if(!StringUtils.equalsAnyIgnoreCase(stage.getStage(), StageEnum.EVT.getStage(), StageEnum.DVT.getStage(),
                    StageEnum.PVT.getStage(), StageEnum.MP.getStage())) {
                reworkScrapCost.setReworkAmount(-1D);
                reworkScrapCost.setScrapAmount(-1D);
                reworkScrapCost.setAmount(-1D);
            }
            reworkScrapCostList.add(reworkScrapCost);
        }
        repository.saveAll(reworkScrapCostList);
    }

    @Override
    public void computeReworkScrapCost(String project) {
        log.info("计算机种的重工报废费用 " + project);
        List<ReworkScrapCost> reworkScrapCostList = getReworkScrapCosts(project);
        for(ReworkScrapCost reworkScrapCost : reworkScrapCostList) {
            if(reworkScrapCost.getAmount()!=null && reworkScrapCost.getAmount() == -1) continue;
            Double reworkAmount = null;
            Double scrapAmount = null;
            // Array
            List<ReworkScrapCostArray> arrayList = reworkScrapCostDetailService.getReworkScrapCostArray(
                    reworkScrapCost.getProject(), reworkScrapCost.getStage(), reworkScrapCost.getTime());
            for(ReworkScrapCostArray array : arrayList) {
                // Rework为重工，其他为报废
                if(StringUtils.equalsIgnoreCase(array.getEvtCate(), "Rework")) {
                    reworkAmount = DoubleUtil.sum(reworkAmount, array.getAmount());
                } else {
                    scrapAmount = DoubleUtil.sum(scrapAmount, array.getAmount());
                }
            }
            // CELL
            List<ReworkScrapCostCell> cellList = reworkScrapCostDetailService.getReworkScrapCostCell(
                    reworkScrapCost.getProject(), reworkScrapCost.getStage(), reworkScrapCost.getTime());
            for(ReworkScrapCostCell cell : cellList) {
                // Rework为重工，其他为报废
                if(StringUtils.equalsIgnoreCase(cell.getEvtCate(), "Rework")) {
                    reworkAmount = DoubleUtil.sum(reworkAmount, cell.getAmount());
                } else {
                    scrapAmount = DoubleUtil.sum(scrapAmount, cell.getAmount());
                }
            }

            // lcm
            List<ReworkScrapCostLcm> lcmList = reworkScrapCostDetailService.getReworkScrapCostLcm(
                    reworkScrapCost.getProject(), reworkScrapCost.getStage(), reworkScrapCost.getTime());
            for(ReworkScrapCostLcm lcm : lcmList) {
                // Rework为重工，其他为报废
                if(StringUtils.equalsIgnoreCase(lcm.getEvtCate(), "Rework")) {
                    reworkAmount = DoubleUtil.sum(reworkAmount, lcm.getAmount());
                } else {
                    scrapAmount = DoubleUtil.sum(scrapAmount, lcm.getAmount());
                }
            }
            reworkScrapCost.setReworkAmount(reworkAmount);
            reworkScrapCost.setScrapAmount(scrapAmount);
            reworkScrapCost.setAmount(DoubleUtil.sum(reworkScrapCost.getReworkAmount(), reworkScrapCost.getScrapAmount()));
        }
        repository.saveAll(reworkScrapCostList);
    }
}
