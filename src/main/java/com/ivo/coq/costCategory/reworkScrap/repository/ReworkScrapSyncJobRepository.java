package com.ivo.coq.costCategory.reworkScrap.repository;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapSyncJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapSyncJobRepository extends JpaRepository<ReworkScrapSyncJob, Long> {

    List<ReworkScrapSyncJob> findByProject(String project);

    List<ReworkScrapSyncJob> findByProjectOrderByPlant(String project);
}
