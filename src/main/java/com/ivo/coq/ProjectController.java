package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.project.service.EngineeringExperimentService;
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

    @Autowired
    public ProjectController(SampleService sampleService, EngineeringExperimentService engineeringExperimentService) {
        this.sampleService = sampleService;
        this.engineeringExperimentService = engineeringExperimentService;
    }

    @GetMapping("/sample/{project}")
    public PageResult getSamples(@PathVariable("project") String project) {
        return ResultUtil.successPage(sampleService.getSamples(project));
    }

    @GetMapping("/engineeringExperiment/{project}")
    public PageResult getEngineeringExperiment(@PathVariable("project") String project) {
        return ResultUtil.successPage(engineeringExperimentService.getEngineeringExperiments(project));
    }
}
