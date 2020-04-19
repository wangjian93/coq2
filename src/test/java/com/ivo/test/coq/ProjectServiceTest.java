package com.ivo.test.coq;

import com.ivo.coq.project.service.*;
import com.ivo.test.AbstractTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectServiceTest extends AbstractTest {

    @Autowired
    private SampleService sampleService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StageService stageService;

    @Autowired
    private EngineeringExperimentService engineeringExperimentService;


    private static final String PROJECT = "N1568V R0";

    /**
     * 获取实验信息
     */
    @Test
    public void a1_syncSamples() {
        sampleService.syncSamples(PROJECT);
    }

    /**
     * 获取进度
     */
    @Test
    public void a2_syncMilestone() {
        milestoneService.syncMilestone(PROJECT);
    }

    /**
     * 获取项目成员
     */
    @Test
    public void a3_syncMember() {
        memberService.syncMember(PROJECT);
    }

    /**
     * 获取阶段
     */
    @Test
    public void a4_generateStage() {
        stageService.generateStage(PROJECT);
    }

    /**
     * 生成工程实验
     */
    @Test
    public void b1_generateEngineeringExperiment() {
        engineeringExperimentService.generateEngineeringExperiment(PROJECT);
    }

    /**
     * 同步工厂
     */
    @Test
    public void b2_syncEngineeringExperimentPlant() {
        engineeringExperimentService.syncEngineeringExperimentPlant(PROJECT);
    }

    /**
     * 获取Array/CELL产品
     */
    @Test
    public void b3_syncEngineeringExperimentProduct() {
        engineeringExperimentService.syncEngineeringExperimentProduct(PROJECT);
    }

    /**
     * 获取CELL料号
     */
    @Test
    public void b4_syncEngineeringExperimentMaterial() {
        engineeringExperimentService.syncEngineeringExperimentMaterial(PROJECT);
    }

    /**
     * 获取LCM工单
     */
    @Test
    public void b5_syncEngineeringExperimentWo() {
        engineeringExperimentService.syncEngineeringExperimentWo(PROJECT);
    }
}
