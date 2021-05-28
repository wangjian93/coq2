package com.ivo.coq.station.repository;

import com.ivo.coq.station.entity.StationCostCell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCostCellRepository {

    List<StationCostCell> findByMonthAndProject(String month, String ee);

    StationCostCell findFirstByMonthAndProjectAndStationLike(String month, String project, String station);
}
