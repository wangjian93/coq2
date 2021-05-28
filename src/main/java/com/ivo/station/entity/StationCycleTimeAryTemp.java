package com.ivo.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 临时表
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "temp_Station_CycleTime_Ary")
public class StationCycleTimeAryTemp extends AutoIncreaseEntityModel {
    private String PROD_MODEL_ID;
    private String PEP_CNT;
    private String CR_OPE_ID;
    private String OPE_NAME;
    private String EQPT_ID;
    private double SHT_CNT;
    private double S_CNT;
    private double B_CNT;
    private double R_CNT;
    private double PROC_CNT;
    private double CYCLE_TIME_NOWAIT_B;
    private double CYCLE_TIME_NOWAIT_R;
    private double CYCLE_TIME_NOWAIT_S;
    private double CYCLE_TIME_NOWAIT_M;
}
