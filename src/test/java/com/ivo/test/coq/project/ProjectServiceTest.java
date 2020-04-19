package com.ivo.test.coq.project;

import com.ivo.coq.project.service.*;
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
public class ProjectServiceTest extends AbstractTest {

    @Autowired
    private SampleService sampleService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StageService stageService;

    private static final String PROJECT = "N1568V R0";

    /**
     * 获取实验信息
     */
    @Test
    public void a_syncSamples() {
        sampleService.syncSamples(PROJECT);
        Assert.notEmpty(sampleService.getSamples(PROJECT), "");
    }

    /**
     * 获取进度
     */
    @Test
    public void b_syncMilestone() {
        milestoneService.syncMilestone(PROJECT);
        Assert.notEmpty(milestoneService.getMilestone(PROJECT), "");
    }

    /**
     * 获取项目成员
     */
    @Test
    public void c_syncMember() {
        memberService.syncMember(PROJECT);
        Assert.notEmpty(memberService.getMembers(PROJECT), "");
    }

    /**
     * 获取阶段
     */
    @Test
    public void d_generateStage() {
        stageService.generateStage(PROJECT);
        Assert.notEmpty(stageService.getStage(PROJECT), "");
    }
}
