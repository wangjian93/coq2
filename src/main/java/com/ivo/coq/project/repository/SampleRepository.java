package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface SampleRepository extends JpaRepository<Sample, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<Sample>
     */
    List<Sample> findByProject(String project);
}
