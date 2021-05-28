package com.ivo.coq.station.repository;

import com.ivo.coq.station.entity.StationCostAry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCostAryRepository {

    List<StationCostAry> findByMonthAndProject(String month, String project);

    StationCostAry findFirstByMonthAndProjectAndStationLike(String month, String project, String station);
}
