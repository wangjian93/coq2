package com.ivo.rest.qms.entity;
import lombok.Getter;
import lombok.Setter;

/**
 * QMS-机种OBA费用
 * @author wj
 * @version 1.0
 */
@Setter
@Getter
public class QmsOba {

    /**
     * PE单号
     */
    private String peNumber;

    /**
     * 发生时间
     */
    private String happenTime;

    /**
     * OBA类型
     */
    private String obaType;

    /**
     * 数量
     */
    private Double quantity;

    /**
     * 价格
     */
    private Double price;

    /**
     * 费用
     */
    private Double amount;
}
