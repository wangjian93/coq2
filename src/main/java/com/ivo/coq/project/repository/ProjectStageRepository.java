package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.ProjectStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ProjectStageRepository extends JpaRepository<ProjectStage, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<ProjectStage>
     */
    List<ProjectStage> findByProject(String project);
}
