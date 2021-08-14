package com.ivo.product.repository;

import com.ivo.product.entity.MSC_LOCATION_COST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface LocationCostRepository extends JpaRepository<MSC_LOCATION_COST, Long> {

    List<MSC_LOCATION_COST> findByFabAndDateBetween(String fab, Date fromDate, Date toDate);

    @Query(value = "select month, sum(price) as amount from (\n" +
            "select *,DATE_FORMAT(date,'%Y%m') as month from msc_location_cost \n" +
            "where ver_id = 'BTrip'\n" +
            "and fab =:fab \n" +
            "and date>=:fromDate and date<=:toDate \n" +
            ")t GROUP BY month", nativeQuery = true)
    List<Map> getB_TripAmuntMonth(String fab, Date fromDate, Date toDate);

    @Query(value = "select month, sum(price) as amount from (\n" +
            "select *,DATE_FORMAT(date,'%Y%m') as month from msc_location_cost \n" +
            "where ver_id <> 'BTrip'\n" +
            "and fab =:fab \n" +
            "and date>=:fromDate and date<=:toDate \n" +
            ")t GROUP BY month", nativeQuery = true)
    List<Map> getScrapAmountMonth(String fab, Date fromDate, Date toDate);

    @Query(value = "select *,DATE_FORMAT(date,'%Y%m') as month from msc_location_cost \n" +
            "where ver_id = 'BTrip'\n" +
            "and fab =:fab \n" +
            "and date>=:fromDate and date<=:toDate \n",
            nativeQuery = true)
    List<Map> getB_TripAmuntMonthDetail(String fab, Date fromDate, Date toDate);

    @Query(value = "select *,DATE_FORMAT(date,'%Y%m') as month from msc_location_cost \n" +
            "where ver_id <> 'BTrip'\n" +
            "and fab =:fab \n" +
            "and date>=:fromDate and date<=:toDate \n",
            nativeQuery = true)
    List<Map> getScrapAmountMonthDetail(String fab, Date fromDate, Date toDate);

}
