package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.ProjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ProjectScheduleRepository extends JpaRepository<ProjectSchedule, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<ProjectSchedule>
     */
    List<ProjectSchedule> findByProject(String project);

    /**
     * 根据机种、版本筛选
     * @param project 机种
     * @param version 版本
     * @return List<ProjectSchedule>
     */
    List<ProjectSchedule> findByProjectAndVersion(String project, String version);
}
