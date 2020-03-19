package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.project.service.EngineeringExperimentService;
import com.ivo.coq.project.service.ProjectMemberService;
import com.ivo.coq.project.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq/project")
public class ProjectController {

    private SampleService sampleService;
    private EngineeringExperimentService engineeringExperimentService;
    private ProjectMemberService projectMemberService;

    @Autowired
    public ProjectController(SampleService sampleService, EngineeringExperimentService engineeringExperimentService,
                             ProjectMemberService projectMemberService) {
        this.sampleService = sampleService;
        this.engineeringExperimentService = engineeringExperimentService;
        this.projectMemberService = projectMemberService;
    }

    @GetMapping("/sample/{project}")
    public PageResult getSamples(@PathVariable("project") String project) {
        return ResultUtil.successPage(sampleService.getSamples(project));
    }

    @GetMapping("/engineeringExperiment/{project}")
    public PageResult getEngineeringExperiment(@PathVariable("project") String project) {
        return ResultUtil.successPage(engineeringExperimentService.getEngineeringExperiments(project));
    }

    /**
     * 获取机种的项目成员
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/member/{project}")
    public PageResult getProjectMember(@PathVariable("project") String project) {
        return ResultUtil.successPage(projectMemberService.getMembers(project));
    }
}
