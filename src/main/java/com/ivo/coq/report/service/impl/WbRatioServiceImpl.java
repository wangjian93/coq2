package com.ivo.coq.report.service.impl;

import com.ivo.coq.report.entity.WbRatio;
import com.ivo.coq.report.repository.WbRatioRepository;
import com.ivo.coq.report.service.WbRatioService;
import com.ivo.rest.oracle.OracleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class WbRatioServiceImpl implements WbRatioService {

    private OracleService oracleService;

    private WbRatioRepository wbRatioRepository;

    @Autowired
    public WbRatioServiceImpl(OracleService oracleService, WbRatioRepository wbRatioRepository) {
        this.oracleService = oracleService;
        this.wbRatioRepository = wbRatioRepository;
    }

    @Override
    public void syncWbRatio() {
        wbRatioRepository.deleteAll();
        wbRatioRepository.saveAll(oracleService.getWbRatio());
    }

    @Override
    public List<WbRatio> getWbRatio(Date fromDate, Date toDate, String fabId) {
        List<String> months = getMonthBetween(fromDate, toDate);
        return  wbRatioRepository.findByFabIdAndMonInAndRatioNotNullOrderByMonAsc(fabId, months);
    }

    /**
     * 开始时间结束时间取月份集合
     * @param minDate 开始日期
     * @param maxDate 结束日期
     * @return List<String>
     */
    public static List<String> getMonthBetween(Date minDate, Date maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(minDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(maxDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }
}
