package com.ivo.coq.costCategory.directMaterial.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.directMaterial.entity.ShipmentCostDetail;
import com.ivo.coq.costCategory.directMaterial.repository.ShipmentCostDetailRepository;
import com.ivo.coq.costCategory.directMaterial.service.ShipmentCostDetailService;
import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.entity.EngineeringExperimentWo;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.SampleService;
import com.ivo.rest.oracle.OracleService;
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
public class ShipmentCostDetailServiceImpl implements ShipmentCostDetailService {

    private ShipmentCostDetailRepository repository;

    private SampleService sampleService;

    private OracleService oracleService;

    @Autowired
    public ShipmentCostDetailServiceImpl(ShipmentCostDetailRepository repository,
                                         SampleService sampleService,
                                         OracleService oracleService) {
        this.repository = repository;
        this.sampleService = sampleService;
        this.oracleService = oracleService;
    }

    @Override
    public List<ShipmentCostDetail> getShipmentCostDetail(String project) {
        return repository.findByProject(project);
    }

    @Override
    public List<ShipmentCostDetail> getShipmentCostDetail(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }

    @Override
    public void createShipmentCostDetail(String project) {
        log.info("创建出货费用 " + project);
        repository.deleteAll(getShipmentCostDetail(project));
        List<Sample> sampleList = sampleService.getSamples(project);
        if(sampleList == null) return;
        List<ShipmentCostDetail> shipmentCostDetailList = new ArrayList<>();
        for(Sample sample : sampleList) {
            if(sample.getShipment() == null || sample.getShipment()==0) continue;
            ShipmentCostDetail shipmentCostDetail = new ShipmentCostDetail(sample.getProject(), sample.getStage(), sample.getTime());
            // 出货数量
            shipmentCostDetail.setQuantity(sample.getShipment());
            // 出货单价 = 工单费用 / 工单Shipping数量
            List<EngineeringExperiment> engineeringExperimentList = sample.getEngineeringExperimentList();
            Double shipmentQuantity = null;
            Double woAmount = null;
            for(EngineeringExperiment engineeringExperiment : engineeringExperimentList) {
                List<EngineeringExperimentWo> woList = engineeringExperiment.getWoList();
                for(EngineeringExperimentWo wo : woList) {
                    Double shipmentQuantity_ = oracleService.getWoShippingQty(wo.getWo());
                    Double woAmount_ = oracleService.getWoAmount(wo.getWo());
                    shipmentQuantity = DoubleUtil.sum(shipmentQuantity, shipmentQuantity_);
                    woAmount = DoubleUtil.sum(woAmount, woAmount_);
                }
            }
            if(shipmentQuantity != null && woAmount != null) {
                shipmentCostDetail.setPerAmount(DoubleUtil.divide(woAmount, shipmentQuantity));
            }

            shipmentCostDetail.setAmount(DoubleUtil.multiply(shipmentCostDetail.getQuantity(), shipmentCostDetail.getPerAmount()));
            shipmentCostDetailList.add(shipmentCostDetail);
        }
        repository.saveAll(shipmentCostDetailList);
    }

    @Override
    public void computeShipmentCostDetail(String project) {
        log.info("计算出货费用 " + project);
    }
}
