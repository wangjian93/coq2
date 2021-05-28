package com.ivo.station.repository;

import com.ivo.station.entity.StationCycleTimeAry;
import com.ivo.station.entity.StationCycleTimeCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCycleTimeCellRepository extends JpaRepository<StationCycleTimeCell, Long> {

    /**
     * 统计月份数据
     * @param month 月份
     * @return
     */
    long countByMonth(String month);

    /**
     * 筛选月份
     * @param month 月份
     * @return
     */
    List<StationCycleTimeCell> findByMonth(String month);

    /**
     * 获取cycle time 的分类
     * @param month 月份
     * @param project 机种
     * @return
     */
    @Query(value = "select a.prodId from StationCycleTimeCell a where a.month=:month and a.project=:project GROUP BY a.prodId order by a.prodId")
    List<String> getProd_id(String month, String project);


    /**
     * 机种经过的所有站点费用总和
     * @param month 月份
     * @param project 机种
     * @param prod_id 分类
     * @return
     */
    @Query(value = "select sum(a.amount) from StationCycleTimeCell a where a.month=:month and a.project=:project and a.prodId=:prod_id")
    Double getPerProductAmountCell(String month, String project, String prod_id);

    /**
     * 筛选月份、机种、站点
     * @param month 月份
     * @param project 机种
     * @param station 站点
     * @param prodId 分类
     * @return
     */
    StationCycleTimeCell findFirstByMonthAndProjectAndStationLikeAndProdId(String month, String project, String station, String prodId);

    /**
     * 机种重工站点之前所有经过的站点费用总和
     * @param month 月份
     * @param project 机种
     * @param station 重工站点
     * @param prod_id 分类
     * @return
     */
    @Query(value = "select sum(a.reworkAmount) from StationCycleTimeCell a where a.month=:month and a.project=:project and station<=:station and a.prodId=:prod_id")
    Double getPerReworkAmountCell(String month, String project, String station, String prod_id);


    /**
     * 获取机种有维护站点数据的最近的一个月份
     * @param project 机种
     * @return String
     */
    @Query(value = "select a.month from Station_Cycle_Time_Cell a where a.project=:project order by a.month desc limit 1", nativeQuery = true)
    String getLastMonth(String project);

    /**
     *
     * @param month 月份
     * @param project 机种
     * @return
     */
    double countByMonthAndProject(String month, String project);

}
