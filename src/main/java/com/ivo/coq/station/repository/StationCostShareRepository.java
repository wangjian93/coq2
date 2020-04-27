package com.ivo.coq.station.repository;

import com.ivo.coq.station.entity.StationCostShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCostShareRepository extends JpaRepository<StationCostShare, Long> {

    List<StationCostShare> findByProject(String project);

    StationCostShare findFirstByProjectAndFactoryAndStation(String project, String factory, String station);

    StationCostShare findFirstByProjectAndFactoryAndStationLike(String project, String factory, String station);

    @Query(value = "from com.ivo.coq.station.entity.StationCostShare s where s.project= :project and s.factory= :factory and s.sort <= :sort")
    List<StationCostShare> getBeforeStation(@Param("project") String project, @Param("factory") String factory,
                                            @Param("sort") int sort);

    List<StationCostShare> findByProjectAndFactory(String project, String factory);
}
