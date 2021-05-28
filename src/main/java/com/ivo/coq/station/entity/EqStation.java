package com.ivo.coq.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * EQ匹配工作中心
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity(name = "coq_eq_station")
public class EqStation extends AutoIncreaseEntityModel {

    /**
     * 机台群组
     */
    private String eq;

    /**
     * 工作中心
     */
    private String workCenter;

    private String  workCenter_;

    private String description;

    private String memo;

    /**
     * 厂别
     */
    private String fab;
}



