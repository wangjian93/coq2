package com.ivo.coq.project.repository;

import com.ivo.coq.project.entity.EngineeringExperimentWo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface EngineeringExperimentWoRepository extends JpaRepository<EngineeringExperimentWo, Long> {

    List<EngineeringExperimentWo> findByProject(String project);


    @Query(value = "select distinct wo from  EngineeringExperimentWo where project=:project and ee=:ee")
    List<String> getWoByEe(String project, String ee);
}
