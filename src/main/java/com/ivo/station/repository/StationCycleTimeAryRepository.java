package com.ivo.station.repository;

import com.ivo.station.entity.StationCycleTimeAry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCycleTimeAryRepository extends JpaRepository<StationCycleTimeAry, Long> {

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
    List<StationCycleTimeAry> findByMonth(String month);

    /**
     * 机种经过的所有站点费用总和
     * @param month 月份
     * @param project 机种
     * @return
     */
    @Query(value = "select sum(a.amount) from StationCycleTimeAry a where a.month=:month and a.project=:project")
    Double getPerProductAmountAry(String month, String project);

    /**
     * 筛选月份、机种、站点
     * @param month 月份
     * @param project 机种
     * @param station 站点
     * @return
     */
    StationCycleTimeAry findFirstByMonthAndProjectAndStationLike(String month, String project, String station);

    /**
     * 机种重工站点之前所有经过的站点费用总和
     * @param month 月份
     * @param project 机种
     * @param station 重工站点
     * @return
     */
    @Query(value = "select sum(a.reworkAmount) from StationCycleTimeAry a where a.month=:month and a.project=:project and station<=:station")
    Double getPerReworkAmountAry(String month, String project, String station);


    /**
     * 获取机种有维护站点数据的最近的一个月份
     * @param project 机种
     * @return String
     */
    @Query(value = "select a.month from Station_CycleTime_Ary a where a.project=:project order by a.month desc limit 1", nativeQuery = true)
    String getLastMonth(String project);

    /**
     * 判断当月机种有没有数据
     * @param month 月份
     * @param project 机种
     * @return
     */
    double countByMonthAndProject(String month, String project);

}
