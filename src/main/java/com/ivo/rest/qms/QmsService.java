package com.ivo.rest.qms;

import com.ivo.rest.qms.entity.QmsOba;
import com.ivo.rest.qms.entity.QmsVerification;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface QmsService {

    /**
     * 从QMS获取机种的验证信息，数据源QMS新产品验证进度管理
     * @param project 机种
     * @return
     */
    List<QmsVerification> getQmsVerification(String project);

    /**
     * 从QMS获取机种的OBA
     * @param project 机种
     * @return
     */
    List<QmsOba> getQmsOba(String project);
}
