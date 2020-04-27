package com.ivo.coq.costCategory.reworkScrap.repository;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostLcm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCostLcmRepository extends JpaRepository<ReworkScrapCostLcm, Long> {

    List<ReworkScrapCostLcm> findByProject(String project);
    List<ReworkScrapCostLcm> findByProjectAndStageAndTime(String project, String stage, Integer time);
    List<ReworkScrapCostLcm> findByJobId(Long jobId);
}
