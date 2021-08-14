package com.ivo.coq.report.service.impl;

import com.ivo.coq.report.repository.WbRatioRepository;
import com.ivo.coq.report.service.WbRatioService;
import com.ivo.product.entity.ObaMonth;
import com.ivo.product.repository.LocationCostRepository;
import com.ivo.product.repository.ObaMonthRepository;
import com.ivo.rest.oracle.OracleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class WbRatioServiceImpl implements WbRatioService {

    private OracleService oracleService;

    private WbRatioRepository wbRatioRepository;

    @Resource
    private LocationCostRepository locationCostRepository;

    @Resource
    private ObaMonthRepository obaMonthRepository;

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
    public Map getWbRatio(List<String> monthList, String fabId) throws ParseException {
        String FAB_ID = fabId;
        Map<String, Map> month_map = new HashMap<>();
        for(String month : monthList) {
            Map<String, Double> subMap = new HashMap<>();
            subMap.put("b_trip", 0d);
            subMap.put("scrap", 0d);
            subMap.put("oba", 0d);
            subMap.put("product", 0d);
            month_map.put(month, subMap);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        List<Map> list_btrip = locationCostRepository.getB_TripAmuntMonth(FAB_ID, sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1)));
        List<Map> list_scrap = locationCostRepository.getScrapAmountMonth(FAB_ID, sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1)));
        for(Map map : list_btrip) {
            String month = (String) map.get("month");
            double amount = (double) map.get("amount");
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("b_trip", amount);
        }
        for(Map map : list_scrap) {
            String month = (String) map.get("month");
            double amount = (double) map.get("amount");
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("scrap", amount);
        }

        String fab = FAB_ID;
        if(fab.equals("WB_CEL")) fab = "CELL";
        if(fab.equals("WB_LCM")) fab = "LCM";
        List<ObaMonth> obaMonthList = obaMonthRepository.findByFabAndMonthIn(fab, monthList);
        for(ObaMonth obaMonth : obaMonthList) {
            String month = obaMonth.getMonth();
            double amount = obaMonth.getAmount();
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("oba", amount);
        }

        return month_map;
    }

    @Override
    public List<Map> getWbRatio(Date fromDate, Date toDate, String fabId) {
        List<String> months = getMonthBetween(fromDate, toDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return  wbRatioRepository.getWebFailureRate(sdf.format(fromDate), sdf.format(toDate), fabId);
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
