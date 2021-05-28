package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * LCM入库的数量
 * 来源SAP mard表
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Mard")
public class Mard extends AutoIncreaseEntityModel {

    /**
     * 成品料号
     */
    private String MATNR;

    /**
     * 工厂
     */
    private String WERKS;

    /**
     * 仓位
     */
    private String LGORT;

    /**
     * 入库数量
     */
    private double LABST;

    /**
     * 入库时间
     */
    private Date ERSDA;

    /**
     * 料号价格
     * 通过查询EXF.SZA_PSI_MBEW
     */
    private double price;
}
