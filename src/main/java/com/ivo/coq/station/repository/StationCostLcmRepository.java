package com.ivo.coq.station.repository;

import com.ivo.coq.station.entity.StationCostLcm;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCostLcmRepository  {

    StationCostLcm findFirstByMonthAndProjectAndProductAndStationLike(String month, String project, String product, String station);
}
