package com.ivo.coq.costCategory.directMaterial.service.impl;

import com.ivo.coq.costCategory.directMaterial.entity.OutsourcingThinningCostDetail;
import com.ivo.coq.costCategory.directMaterial.repository.OutsourcingThinningCostDetailRepository;
import com.ivo.coq.costCategory.directMaterial.service.OutsourcingThinningCostDetailService;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.SampleService;
import com.ivo.rest.bm.BmService;
import com.ivo.rest.bm.entity.BmModel;
import com.ivo.rest.eifdb.EifService;
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
public class OutsourcingThinningCostDetailServiceImpl implements OutsourcingThinningCostDetailService {

    private OutsourcingThinningCostDetailRepository repository;

    private SampleService sampleService;

    private EifService eifService;

    private BmService bmService;

    @Autowired
    public OutsourcingThinningCostDetailServiceImpl(OutsourcingThinningCostDetailRepository repository,
                                                    SampleService sampleService,
                                                    EifService eifService,
                                                    BmService bmService) {
        this.repository = repository;
        this.sampleService = sampleService;
        this.eifService = eifService;
        this.bmService = bmService;
    }

    @Override
    public List<OutsourcingThinningCostDetail> getOutsourcingThinningCostDetails(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public List<OutsourcingThinningCostDetail> getOutsourcingThinningCostDetails(String project, String stage, Integer time) {
        return repository.findByProjectAndStageAndTimeOrderById(project, stage, time);
    }

    @Override
    public void syncOutsourcingThinningCostDetail(String project) {
        log.info("同步外包薄化费用 " + project);
        repository.deleteAll(getOutsourcingThinningCostDetails(project));
        List<Sample> sampleList = sampleService.getSamples(project);
        if(sampleList == null || sampleList.size() == 0) return;
        for(Sample sample : sampleList) {
            String  oee = sample.getOrderNumber();
            // 判断是否OEE单
            if(StringUtils.containsIgnoreCase(oee, "OEE")) {
                String pr = eifService.getPrByOee(oee);
                if(StringUtils.isEmpty(pr)) continue;
                List<BmModel> bmModelList = bmService.getBmOutsourcingThinning(pr);
                if(bmModelList == null || bmModelList.size() == 0) continue;
                List<OutsourcingThinningCostDetail> otcList = new ArrayList<>();
                for(BmModel b : bmModelList) {
                    OutsourcingThinningCostDetail outsourcingThinningCostDetail = new OutsourcingThinningCostDetail(
                            project, sample.getStage(), sample.getTime(), oee);
                    outsourcingThinningCostDetail.setPr(b.getPr().trim());
                    outsourcingThinningCostDetail.setPrDrafter(b.getPrDrafter().trim());
                    outsourcingThinningCostDetail.setPrDrafterDate(b.getPrDrafterDate());
                    outsourcingThinningCostDetail.setPrItem(b.getPrItem().trim());
                    outsourcingThinningCostDetail.setBudgetType(b.getBudgetType().trim());
                    outsourcingThinningCostDetail.setMaterialGroup(b.getMaterialGroup().trim());
                    outsourcingThinningCostDetail.setMaterialGroupName(b.getMaterialGroupName().trim());
                    outsourcingThinningCostDetail.setBudgetNumber(b.getBudgetNumber().trim());
                    outsourcingThinningCostDetail.setSpecification(b.getSpecification().trim());
                    outsourcingThinningCostDetail.setUnitPrice(b.getUnitPrice());
                    outsourcingThinningCostDetail.setQuantity(b.getQuantity());
                    outsourcingThinningCostDetail.setCurrency(b.getCurrency().trim());
                    outsourcingThinningCostDetail.setPrAmount(b.getPrAmount());
                    outsourcingThinningCostDetail.setPoAmount(b.getPoAmount());
                    otcList.add(outsourcingThinningCostDetail);
                }
                repository.saveAll(otcList);
            }
        }
    }
}
