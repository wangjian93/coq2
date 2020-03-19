package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.ProjectSchedule;
import com.ivo.coq.project.repository.ProjectScheduleRepository;
import com.ivo.coq.project.service.ProjectScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ProjectScheduleServiceImpl implements ProjectScheduleService {

    private ProjectScheduleRepository repository;

    @Autowired
    public ProjectScheduleServiceImpl(ProjectScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProjectSchedule> getLatestSchedules(String project, String version) {
        // TODO...
        return null;
    }

    @Override
    public Map<String, List<ProjectSchedule>> getSchedules(String project) {
        List<ProjectSchedule> projectScheduleList = repository.findByProjectOrderById(project);
        Map<String, List<ProjectSchedule>> map = new HashMap<>();
        for(ProjectSchedule projectSchedule : projectScheduleList) {
            if(map.get(projectSchedule.getVersion()) == null) {
                map.put(projectSchedule.getVersion(), new ArrayList<>());
            }
            map.get(projectSchedule.getVersion()).add(projectSchedule);
        }
        return map;
    }

    @Override
    public Date getStageDate(String project, String stage) {
        // TODO...
        return null;
    }
}
