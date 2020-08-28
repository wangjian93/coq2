package com.ivo.coq.costCategory.directMaterial.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.directMaterial.entity.ShipmentCostDetail;
import com.ivo.coq.costCategory.directMaterial.repository.ShipmentCostDetailRepository;
import com.ivo.coq.costCategory.directMaterial.service.ShipmentCostDetailService;
import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.entity.EngineeringExperimentWo;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.SampleService;
import com.ivo.rest.eifdb.EifService;
import com.ivo.rest.oracle.OracleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private EifService eifService;

    @Autowired
    public ShipmentCostDetailServiceImpl(ShipmentCostDetailRepository repository,
                                         SampleService sampleService,
                                         OracleService oracleService,
                                         EifService eifService) {
        this.repository = repository;
        this.sampleService = sampleService;
        this.oracleService = oracleService;
        this.eifService = eifService;
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
        List<Sample> lcmSampleList = new ArrayList<>();
        //选择LCM的
        for(Sample sample : sampleList) {
            if(!StringUtils.equalsIgnoreCase(sample.getProcess(), "Module")) continue;
            lcmSampleList.add(sample);
        }
        if(lcmSampleList.size()==0) return;

        List<ShipmentCostDetail> shipmentCostDetailList = new ArrayList<>();
        List<Map> shipmentMapList = eifService.getShipment(project.split(" ")[0]);
        for(int i=0; i<lcmSampleList.size(); i++) {
            Sample sample = lcmSampleList.get(i);
            Date startDate = sample.getInDate();
            Date endDate = null;
            if(i<lcmSampleList.size()-1) {
                endDate = lcmSampleList.get(i+1).getInDate();
            }

            // 出货数量
            Double shipmentQty = null;
            if(shipmentMapList != null && shipmentMapList.size()>0) {
                for(Map map : shipmentMapList) {
                    Double qty = null;
                    if(map.get("Qty") != null) {
                        qty = ((Integer)map.get("Qty")).doubleValue();
                    }
                    if(qty == null) continue;
                    Date date =(Date) map.get("RequestDate");
                    if( (endDate!=null && startDate.before(date) && endDate.after(date))
                            || (endDate==null && startDate.before(
                                    date))) {
                        shipmentQty = DoubleUtil.sum(shipmentQty, qty);
                    }
                }
            }

            if(shipmentQty == null) continue;
            ShipmentCostDetail shipmentCostDetail = new ShipmentCostDetail(sample.getProject(), sample.getStage(), sample.getTime());
            shipmentCostDetail.setQuantity(shipmentQty);

            // 出货单价 = 工单费用 / 工单Shipping数量
            List<EngineeringExperiment> engineeringExperimentList = sample.getEngineeringExperimentList();
            Double shipmentQuantity = null;
            Double woAmount = null;
            if(engineeringExperimentList != null) {
                for(EngineeringExperiment engineeringExperiment : engineeringExperimentList) {
                    List<EngineeringExperimentWo> woList = engineeringExperiment.getWoList();
                    for(EngineeringExperimentWo wo : woList) {
                        Double shipmentQuantity_ = oracleService.getWoShippingQty(wo.getWo());
                        Double woAmount_ = oracleService.getWoAmount(wo.getWo());
                        shipmentQuantity = DoubleUtil.sum(shipmentQuantity, shipmentQuantity_);
                        woAmount = DoubleUtil.sum(woAmount, woAmount_);
                    }
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
