package com.ivo.coq.costCategory.reworkScrap.repository;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostArray;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostCell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ReworkScrapCostCellRepository extends JpaRepository<ReworkScrapCostCell, Long> {

    List<ReworkScrapCostCell> findByProject(String project);
    List<ReworkScrapCostCell> findByProjectAndStageAndTime(String project, String stage, Integer time);
    List<ReworkScrapCostCell> findByJobId(Long jobId);


}
