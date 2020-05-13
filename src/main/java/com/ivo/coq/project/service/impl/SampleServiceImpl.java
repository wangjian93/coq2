package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.repository.SampleRepository;
import com.ivo.coq.project.service.SampleService;
import com.ivo.rest.plm.PlmService;
import com.ivo.rest.plm.entity.PlmSample;
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
public class SampleServiceImpl implements SampleService {

    private static final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

    private SampleRepository repository;

    private PlmService plmService;

    @Autowired
    public SampleServiceImpl(SampleRepository repository, PlmService plmService) {
        this.repository = repository;
        this.plmService = plmService;
    }

    @Override
    public List<Sample> getSamples(String project) {
        return repository.findByProjectOrderById(project);
    }

    @Override
    public void syncSamples(String project) {
        logger.info("从PLM同步机种的实验信息 " + project);
        List<PlmSample> plmSampleList = plmService.getProjectSample(project);
        repository.deleteAll(getSamples(project));
        List<Sample> sampleList = new ArrayList<>();
        for(PlmSample plmSample : plmSampleList) {
            Sample sample = new Sample();
            sample.setProject(project);
            sample.setStage(StringUtils.isEmpty(plmSample.getStage()) ? null : plmSample.getStage().trim().toUpperCase());
            sample.setTime(plmSample.getTime());
            sample.setProcess(StringUtils.isEmpty(plmSample.getProcess()) ? null : plmSample.getProcess().trim());
            sample.setProcessTime(StringUtils.isEmpty(plmSample.getProcessTime()) ? null : plmSample.getProcessTime().trim());
            sample.setInDate(plmSample.getInDate());
            sample.setOutDate(plmSample.getOutDate());
            sample.setInQuantity(plmSample.getInQuantity());
            sample.setOutQuantity(plmSample.getOutQuantity());
            sample.setOrderNumber(StringUtils.isEmpty(plmSample.getOrderNumber()) ? null : plmSample.getOrderNumber().trim().toUpperCase());
            sample.setShipment(plmSample.getShipment());
            sampleList.add(sample);
        }
        repository.saveAll(sampleList);
    }

}
