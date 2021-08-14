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
@Table(name = "MSC_LOCATION_COST")
public class MSC_LOCATION_COST extends AutoIncreaseEntityModel {

    String fab;

    Date date;

    String prod_id;

    String ver_id;

    double price;

    double amount;
}
