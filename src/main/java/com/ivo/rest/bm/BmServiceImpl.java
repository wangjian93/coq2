package com.ivo.rest.bm;

import com.ivo.rest.bm.entity.BmModel;
import com.ivo.rest.bm.mapper.BmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class BmServiceImpl implements BmService {

    private BmMapper bmMapper;

    @Autowired
    public BmServiceImpl(BmMapper bmMapper) {
        this.bmMapper = bmMapper;
    }

    @Override
    public List<BmModel> getBmOutsourcingThinning(String pr) {
        log.info("数据接口：PR获取机种的外包薄化费用 " + pr);
        return bmMapper.getBmOutsourcingThinning(pr);
    }

    @Override
    public List<BmModel> getBmJig(String project, Date fromDate, Date toDate) {
        return bmMapper.getBmJig(project, fromDate, toDate);
    }

    @Override
    public List<BmModel> getBmVerification(String project, Date fromDate, Date toDate) {
        return bmMapper.getBmVerification(project, fromDate, toDate);
    }
}
