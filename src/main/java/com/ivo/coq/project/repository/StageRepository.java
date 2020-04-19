package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface StageRepository extends JpaRepository<Stage, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<Stage>
     */
    List<Stage> findByProject(String project);
}
