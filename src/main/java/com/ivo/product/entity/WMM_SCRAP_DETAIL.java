package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "WMM_SCRAP_DETAIL")
public class WMM_SCRAP_DETAIL extends AutoIncreaseEntityModel {

    Date fabDate;

    String mon;

    String track_num;

    String material_fk;

    String fab;
}
