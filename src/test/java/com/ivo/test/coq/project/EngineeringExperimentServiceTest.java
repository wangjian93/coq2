package com.ivo.test.coq.project;

import com.ivo.coq.project.service.EngineeringExperimentService;
import com.ivo.coq.project.service.SampleService;
import com.ivo.test.AbstractTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * @author wj
 * @version 1.0
 */
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EngineeringExperimentServiceTest extends AbstractTest {

    @Autowired
    private EngineeringExperimentService engineeringExperimentService;

    @Autowired
    private SampleService sampleService;

    private static final String PROJECT = "N1568V R0";

    /**
     * 获取实验信息
     */
    @Test
    public void a1_syncSamples() {
        sampleService.syncSamples(PROJECT);
        Assert.notEmpty(sampleService.getSamples(PROJECT), "");
    }

    /**
     * 生成工程实验
     */
    @Test
    public void a2_generateEngineeringExperiment() {
        engineeringExperimentService.generateEngineeringExperiment(PROJECT);
        Assert.notEmpty(engineeringExperimentService.getEngineeringExperiments(PROJECT), "");
    }

    /**
     * 同步工厂
     */
    @Test
    public void b_syncEngineeringExperimentPlant() {
        engineeringExperimentService.syncEngineeringExperimentPlant(PROJECT);
    }

    /**
     * 获取产品
     */
    @Test
    public void c_syncEngineeringExperimentProduct() {
        engineeringExperimentService.syncEngineeringExperimentProduct(PROJECT);
        Assert.notEmpty(engineeringExperimentService.getEngineeringExperimentProduct(PROJECT), "");
    }

    /**
     * 获取料号
     */
    @Test
    public void d_syncEngineeringExperimentMaterial() {
        engineeringExperimentService.syncEngineeringExperimentMaterial(PROJECT);
        Assert.notEmpty(engineeringExperimentService.getEngineeringExperimentMaterial(PROJECT), "");
    }

    /**
     * 获取工单
     */
    @Test
    public void e_syncEngineeringExperimentWo() {
        engineeringExperimentService.syncEngineeringExperimentWo(PROJECT);
        Assert.notEmpty(engineeringExperimentService.getEngineeringExperimentWo(PROJECT), "");
    }
}
