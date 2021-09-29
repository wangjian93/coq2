package com.ivo.station.repository;

import com.ivo.station.entity.StationCycleTimeAry;
import com.ivo.station.entity.StationMachineTimeLcm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface StationMachineTimeLcmRepository extends JpaRepository<StationMachineTimeLcm, Long> {

    /**
     * 获取维护站点数据的最近的一个月份
     * @param matnr 成品料号
     * @return String
     */
    @Query(value = "select a.month from station_machine_time_lcm a where a.matnr=:matnr order by a.month desc limit 1", nativeQuery = true)
    String getLastMonth(String matnr);

    /**
     * 判断当月机种有没有数据
     * @param month 月份
     * @return
     */
    double countByMonthAndMatnr(String month, String matnr);


    /**
     * 机种经过的所有站点费用总和
     * @param month 月份
     * @param matnr 机种
     * @return
     */
    @Query(value = "select sum(a.amount) from station_machine_time_lcm a where a.month=:month and a.matnr=:matnr ", nativeQuery = true)
    Double getPerProductAmountLcm(String month, String matnr);

    /**
     * 匹配月份跟机种
     * @param month
     * @param matnr
     * @return
     */
    @Query(value = "select month, matnr from station_machine_time_lcm a \n" +
            "where a.month>=:month and a.matnr like :matnr \n" +
            "GROUP BY month,matnr\n" +
            "order by month", nativeQuery = true)
    List<Map> matchMatnrAndMonth(String month, String matnr);

    /**
     * 筛选月份、机种、站点
     * @param month 月份
     * @param matnr 机种
     * @param station 站点
     * @return
     */
    StationMachineTimeLcm findFirstByMonthAndMatnrAndStationLike(String month, String matnr, String station);

    /**
     * 机种重工站点之前所有经过的站点费用总和
     * @param month 月份
     * @param matnr 机种
     * @param station 重工站点
     * @return
     */
    @Query(value = "select sum(a.reworkAmount) from StationMachineTimeLcm a where a.month=:month and a.matnr=:matnr and station<=:station")
    Double getPerReworkAmountLcm(String month, String matnr, String station);

}
