package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.EngineeringExperimentService;
import com.ivo.coq.project.service.MemberService;
import com.ivo.coq.project.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq/project")
public class ProjectController {

    private SampleService sampleService;
    private EngineeringExperimentService engineeringExperimentService;
    private MemberService memberService;

    @Autowired
    public ProjectController(SampleService sampleService, EngineeringExperimentService engineeringExperimentService,
                             MemberService memberService) {
        this.sampleService = sampleService;
        this.engineeringExperimentService = engineeringExperimentService;
        this.memberService = memberService;
    }

    @GetMapping("/sample/{project}")
    public PageResult getSamples(@PathVariable("project") String project) {
        return ResultUtil.successPage(sampleService.getSamples(project));
    }

    @GetMapping("/engineeringExperiment/{project}")
    public PageResult getEngineeringExperiment(@PathVariable("project") String project) {
        List<Sample> sampleList = sampleService.getSamples(project);
        List<EngineeringExperiment> engineeringExperimentList = new ArrayList<>();
        for(Sample sample : sampleList) {
            List list = sample.getEngineeringExperimentList();
            if(list == null || list.size()==0) {
                EngineeringExperiment engineeringExperiment = new EngineeringExperiment();
                engineeringExperiment.setProject(sample.getProject());
                engineeringExperiment.setSample(sample);
                engineeringExperiment.setProductList(new ArrayList<>());
                engineeringExperiment.setMaterialList(new ArrayList<>());
                engineeringExperiment.setWoList(new ArrayList<>());
                list.add(engineeringExperiment);
            }
            engineeringExperimentList.addAll(list);
        }
        return ResultUtil.successPage(engineeringExperimentList);
    }

    /**
     * 获取机种的项目成员
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/member/{project}")
    public PageResult getProjectMember(@PathVariable("project") String project) {
        return ResultUtil.successPage(memberService.getMembers(project));
    }
}
