package com.ivo.coq.costCategory.production.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.production.entity.ProductionCostDetail;
import com.ivo.coq.costCategory.production.repository.ProductionCostDetailRepository;
import com.ivo.coq.costCategory.production.service.ProductionCostDetailService;
import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.SampleService;
import com.ivo.coq.station.entity.StationCostShare;
import com.ivo.coq.station.service.StationCostShareService;
import lombok.extern.slf4j.Slf4j;
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
public class ProductionCostDetailServiceImpl implements ProductionCostDetailService {

    private ProductionCostDetailRepository repository;

    private SampleService sampleService;

    private StationCostShareService stationCostShareService;

    @Autowired
    public ProductionCostDetailServiceImpl(ProductionCostDetailRepository repository, SampleService sampleService,
                                           StationCostShareService stationCostShareService) {
        this.repository = repository;
        this.sampleService =  sampleService;
        this.stationCostShareService = stationCostShareService;
    }

    @Override
    public List<ProductionCostDetail> getProductionCostDetail(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<ProductionCostDetail> getProductionCostDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void syncProductionCostDetail(String project) {
        log.info("同步创建生产费用详细 " + project);
        repository.deleteAll(getProductionCostDetail(project));
        List<Sample> sampleList = sampleService.getSamples(project);
        if(sampleList == null) return;
        List<ProductionCostDetail> list = new ArrayList<>();
        for(Sample sample : sampleList) {
            ProductionCostDetail productionCostDetail = new ProductionCostDetail(sample.getProject(), sample.getStage(), sample.getTime());
            productionCostDetail.setProcess(sample.getProcess());
            productionCostDetail.setOrderNumber(sample.getOrderNumber());
            productionCostDetail.setInQuantity(sample.getInQuantity());
            List<EngineeringExperiment> engineeringExperimentList = sample.getEngineeringExperimentList();
            if(engineeringExperimentList != null && engineeringExperimentList.size()>0) {
                productionCostDetail.setPlant(engineeringExperimentList.get(0).getPlant());
            }
            list.add(productionCostDetail);
        }
        repository.saveAll(list);
    }

    @Override
    public void computeProductionCostDetail(String project) {
        log.info("计算生产费用详细 " + project);
        List<ProductionCostDetail> productionCostDetailList = getProductionCostDetail(project);
        for(ProductionCostDetail productionCostDetail : productionCostDetailList) {
            // 机种把版本去掉
            String project_ = productionCostDetail.getProject().split(" ")[0];
            Double perProductAmount = stationCostShareService.getProductAmount(project_, productionCostDetail.getPlant());
            productionCostDetail.setPerAmount(perProductAmount);
            productionCostDetail.setAmount(DoubleUtil.multiply(perProductAmount, productionCostDetail.getInQuantity()));
        }
        repository.saveAll(productionCostDetailList);
    }
}
