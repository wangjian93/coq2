package com.ivo.coq.project.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.entity.Stage;
import com.ivo.coq.project.repository.StageRepository;
import com.ivo.coq.project.service.SampleService;
import com.ivo.coq.project.service.StageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Slf4j
@Service
public class StageServiceImpl implements StageService {

    private StageRepository repository;

    private SampleService sampleService;

    @Autowired
    public StageServiceImpl(StageRepository repository, SampleService sampleService) {
        this.repository = repository;
        this.sampleService = sampleService;
    }

    @Override
    public List<Stage> getStage(String project) {
        List<Stage> list = repository.findByProject(project);
        Collections.sort(list);
        return list;
    }

    @Override
    public void generateStage(String project) {
        log.info("根据实验信息生成机种的阶段 " + project);
        List<Sample> sampleList = sampleService.getSamples(project);
        repository.deleteAll(getStage(project));

        Set<String> set = new HashSet<>();
        List<Stage> stageList = new ArrayList<>();
        // NPRB、DESIGN阶段PLM中没有
        Stage nprb = new Stage(project, StageEnum.NPRB.getStage(), null);
        Stage design = new Stage(project, StageEnum.Design.getStage(), null);
        stageList.add(nprb);
        stageList.add(design);
        set.add(nprb.getStage() + nprb.getTime());
        set.add(design.getStage() + nprb.getTime());

        for(Sample sample : sampleList) {
            if(!ArrayUtils.contains(StageEnum.STAGES ,sample.getStage())) {
                continue;
            }
            Stage stage = new Stage(project, sample.getStage().toUpperCase(), sample.getTime());
            if(!set.contains(stage.getStage() + stage.getTime())) {
                stageList.add(stage);
                set.add(stage.getStage() + stage.getTime());
            }
        }
        Collections.sort(stageList);
        repository.saveAll(stageList);
    }

    @Override
    public List<String> getStageNoTime(String project) {
        List<Stage> stageList = getStage(project);
        List<String> list = new ArrayList<>();
        for(Stage stage : stageList) {
            String s = stage.getStage();
            if(!list.contains(s)) {
                list.add(s);
            }
        }
        return list;
    }


}
