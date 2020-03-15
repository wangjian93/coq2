package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.repository.SampleRepository;
import com.ivo.coq.project.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class SampleServiceImpl implements SampleService {

    private SampleRepository repository;

    @Autowired
    public SampleServiceImpl(SampleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Sample> getSamples(String project) {
        return repository.findByProject(project);
    }
}
