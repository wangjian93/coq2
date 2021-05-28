package com.ivo.station.repository;

import com.ivo.station.entity.StationCostLcm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCostLcmRepository extends JpaRepository<StationCostLcm, Long> {

    /**
     * 筛选product、月份，按站点排序
     * @param product 工单product
     * @param month 月份
     * @return  List<StationCostLcm>
     */
    List<StationCostLcm> findByProductAndMonthOrderByStation(String product, String month);

    /**
     * 获取机种有维护站点数据的最近的一个月份
     * @param product product
     * @return String
     */
    @Query(value = "select a.month from Station_Cost_LCM a where a.product=:product order by a.month desc limit 1", nativeQuery = true)
    String getLastMonth(String product);

    /**
     * 筛选product、月份、站点
     * @param product product
     * @param month 月份
     * @param station 站点
     * @return StationCostLcm
     */
    StationCostLcm findFirstByProductAndMonthAndStationLike(String product, String month, String station);
}
