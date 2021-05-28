package com.ivo.coq.costCategory.systemMaintenance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 研发工具折旧维护费用
 * @author wj
 * @version 1.0
 */
@Entity(name = "coq_design_tool")
@Setter
@Getter
public class DesignTool extends AutoIncreaseEntityModel {

    private String type;

    /**
     * 角色
     */
    private String role;

    /**
     * 工具
     */
    private String tool;

    /**
     * 工具使用标准工时/天
     */
    private double useDays;

    /**
     * 折旧费用/年（RMB）
     */
    private double depreciationAmount;

    /**
     * 维护费用/年（RMB）
     */
    private double maintainAmount;


    /**
     * 工具费用
     */
    private double toolAmount;

    private String version;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    private boolean validFlag = true;
}
