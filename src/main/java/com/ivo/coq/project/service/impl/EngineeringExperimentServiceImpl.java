package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.repository.EngineeringExperimentRepository;
import com.ivo.coq.project.service.EngineeringExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class EngineeringExperimentServiceImpl implements EngineeringExperimentService {

    private EngineeringExperimentRepository repository;

    @Autowired
    public EngineeringExperimentServiceImpl(EngineeringExperimentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EngineeringExperiment> getEngineeringExperiments(String project) {
        return repository.findByProject(project);
    }
}
