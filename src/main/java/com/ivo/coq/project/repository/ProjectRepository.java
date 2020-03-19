package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * 根据类型筛选
     * @param type 类型
     * @return List<ProjectStage>
     */
    List<Project> findByTypeOrderById(String type);

    /**
     * 根据类型、尺寸筛选
     * @param type 类型
     * @param size 尺寸
     * @return List<ProjectStage>
     */
    List<Project> findByTypeAndSizeOrderById(String type, String size);

}
