package com.ivo.coq.project.service.impl;

import com.ivo.coq.project.entity.ProjectSchedule;
import com.ivo.coq.project.service.ProjectScheduleService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ProjectScheduleServiceImpl implements ProjectScheduleService {

    @Override
    public List<ProjectSchedule> getSchedules(String project) {
        // TODO...
        return null;
    }

    @Override
    public Date getStageDate(String project, String stage) {
        // TODO...
        return null;
    }
}
