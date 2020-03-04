package com.ivo.common.model;

import com.ivo.constant.ValidStatusConst;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 实体类模型
 * @author wj
 * @version 1.0
 */
@MappedSuperclass
public class EntityModel {

    /**
     * 标识数据是否有效，逻辑删除
     */
    private Byte validFlag = ValidStatusConst.VALID;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改者
     */
    private String updater;
}
