package com.ivo.station.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机台匹配工作中心
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Eq_Work_Center")
public class EqWorkCenter extends AutoIncreaseEntityModel {

    /**
     * ARY CELL LCM
     */
    private String fab;

    /**
     * 机台
     */
    private String eq;

    /**
     * 工作中心
     */
    @Column(name = "work_Center")
    private String workCenter;

    /**
     * 成本中心
     */
    private String cost_center;

    private String description;
}
