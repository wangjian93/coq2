package com.ivo.station.repository;

import com.ivo.station.entity.StationCostCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCostCellRepository extends JpaRepository<StationCostCell, Long> {

    /**
     * 筛选机种、月份，按站点排序
     * @param project 机种
     * @param month 月份
     * @return List<StationCostAry>
     */
    List<StationCostCell> findByProjectAndMonthOrderByStation(String project, String month);


    /**
     * 获取机种有维护站点数据的最近的一个月份
     * @param project 机种
     * @return String
     */
    @Query(value = "select a.month from Station_Cost_Cell a where a.project=:project order by a.month desc limit 1", nativeQuery = true)
    String getLastMonth(String project);

    /**
     * 筛选机种、月份、站点
     * @param project 机种
     * @param month 月份
     * @param station 站点
     * @return StationCostCell
     */
    StationCostCell findFirstByProjectAndMonthAndStationLike(String project, String month, String station);
}
