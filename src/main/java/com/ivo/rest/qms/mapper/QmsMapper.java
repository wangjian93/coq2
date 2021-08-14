package com.ivo.rest.qms.mapper;

import com.ivo.rest.qms.entity.QmsOba;
import com.ivo.rest.qms.entity.QmsVerification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
@Repository
public interface QmsMapper {

    /**
     * 从QMS获取机种的验证信息
     * @param project 机种
     * @return List
     */
    List<QmsVerification> getQmsVerification(String project);

    /**
     * 从QMS获取机种的OBA
     * @param project 机种
     * @return
     */
    List<QmsOba> getQmsOba(String project);

    /**
     * 获取OBA数据
     * @return
     */
    List<Map> getOba();
}
