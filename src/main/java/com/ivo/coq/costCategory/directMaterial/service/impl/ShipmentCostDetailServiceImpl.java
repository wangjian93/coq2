package com.ivo.coq.costCategory.directMaterial.service.impl;

import com.ivo.coq.costCategory.directMaterial.entity.ShipmentCostDetail;
import com.ivo.coq.costCategory.directMaterial.repository.ShipmentCostDetailRepository;
import com.ivo.coq.costCategory.directMaterial.service.ShipmentCostDetailService;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.SampleService;
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

    @Autowired
    public ShipmentCostDetailServiceImpl(ShipmentCostDetailRepository repository,
                                         SampleService sampleService) {
        this.repository = repository;
        this.sampleService = sampleService;
    }

    @Override
    public List<ShipmentCostDetail> getShipmentCostDetail(String project) {
        return repository.findByProject(project);
    }

    @Override
    public void createShipmentCostDetail(String project) {
        log.info("创建出货费用 " + project);
        List<Sample> sampleList = sampleService.getSamples(project);
        if(sampleList == null) return;
        List<ShipmentCostDetail> shipmentCostDetailList = new ArrayList<>();
        for(Sample sample : sampleList) {
            ShipmentCostDetail shipmentCostDetail = new ShipmentCostDetail(sample.getProject(), sample.getStage(), sample.getTime());
            // 出货数量
            // 出货单价
            shipmentCostDetailList.add(shipmentCostDetail);
        }
        repository.saveAll(shipmentCostDetailList);
    }

    @Override
    public void computeShipmentCostDetail(String project) {
        log.info("计算出货费用 " + project);
    }
}
