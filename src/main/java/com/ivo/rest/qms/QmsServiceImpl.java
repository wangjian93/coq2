package com.ivo.rest.qms;

import com.ivo.rest.qms.entity.QmsOba;
import com.ivo.rest.qms.entity.QmsVerification;
import com.ivo.rest.qms.mapper.QmsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class QmsServiceImpl implements QmsService {

    private QmsMapper qmsMapper;

    @Autowired
    public QmsServiceImpl(QmsMapper qmsMapper) {
        this.qmsMapper = qmsMapper;
    }

    @Override
    public List<QmsVerification> getQmsVerification(String project) {
        return qmsMapper.getQmsVerification(project);
    }

    @Override
    public List<QmsOba> getQmsOba(String project) {
        return qmsMapper.getQmsOba(project);
    }
}
