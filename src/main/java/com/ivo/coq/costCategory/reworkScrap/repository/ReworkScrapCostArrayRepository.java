package com.ivo.coq.costCategory.reworkScrap.repository;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostArray;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCostArrayRepository extends JpaRepository<ReworkScrapCostArray, Long> {

    List<ReworkScrapCostArray> findByProject(String project);
    List<ReworkScrapCostArray> findByProjectAndStageAndTime(String project, String stage, Integer time);

    List<ReworkScrapCostArray> findByJobId(Long jobId);

}
