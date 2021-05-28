package com.ivo.product.entity;

import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 总制造费用欧冠
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "Total_Product_Cost")
public class TotalProductCost extends AutoIncreaseEntityModel {

    private String fab;

    private String month;

    private double amount;

    private double price;
}
