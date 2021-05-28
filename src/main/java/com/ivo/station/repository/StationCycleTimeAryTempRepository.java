package com.ivo.station.repository;

import com.ivo.station.entity.StationCycleTimeAryTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCycleTimeAryTempRepository extends JpaRepository<StationCycleTimeAryTemp, Long> {

    /**
     * 将StationCycleTimeAry临时表汇总成整月数据
     * @return
     */
    @Query(value = "select " +
            "PROD_MODEL_ID,PEP_CNT,CR_OPE_ID,OPE_NAME,EQPT_ID,SHT_CNT, " +
            "B_CNT,R_CNT,S_CNT,M_CNT, " +
            "case when B_CNT>0 then B_TIME/B_CNT ELSE 0 END B_TIME, " +
            "case when R_CNT>0 then R_TIME/R_CNT ELSE 0 END R_TIME, " +
            "case when S_CNT>0 then S_TIME/S_CNT ELSE 0 END S_TIME, " +
            "case when M_CNT>0 then M_TIME/M_CNT ELSE 0 END M_TIME " +
            "from ( " +
            "select " +
            "PROD_MODEL_ID,PEP_CNT,CR_OPE_ID,OPE_NAME,EQPT_ID, " +
            "sum(SHT_CNT) as SHT_CNT, " +
            "sum(S_CNT) as S_CNT, sum(B_CNT) as B_CNT, sum(R_CNT) as R_CNT, sum(PROC_CNT) as m_CNT, " +
            "sum(CYCLE_TIME_NOWAIT_B) as B_TIME, " +
            "sum(CYCLE_TIME_NOWAIT_R) as R_TIME, " +
            "sum(CYCLE_TIME_NOWAIT_S) as S_TIME, " +
            "sum(CYCLE_TIME_NOWAIT_M) as M_TIME  " +
            "from temp_station_cycle_time_ary " +
            "GROUP BY PROD_MODEL_ID,PEP_CNT,CR_OPE_ID,OPE_NAME,EQPT_ID " +
            ") T", nativeQuery = true)
    List<Map> sumStationCycleTimeAry();
}
