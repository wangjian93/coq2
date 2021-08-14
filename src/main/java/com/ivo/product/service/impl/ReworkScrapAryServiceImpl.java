package com.ivo.product.service.impl;

import com.ivo.product.entity.ReworkScrapAry;
import com.ivo.product.entity.ReworkScrapMonth;
import com.ivo.product.repository.ReworkScrapAryRepository;
import com.ivo.product.repository.ReworkScrapMonthRepository;
import com.ivo.product.service.ReworkScrapAryService;
import com.ivo.rest.oracle.mapper.OracleMapper;
import com.ivo.station.service.StationCostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ReworkScrapAryServiceImpl implements ReworkScrapAryService {

    @Resource
    private OracleMapper oracleMapper;

    @Resource
    private ReworkScrapAryRepository reworkScrapAryRepository;

    @Resource
    private StationCostService stationCostService;

    @Resource
    private ReworkScrapMonthRepository reworkScrapMonthRepository;

    @Override
    public void syncAryReworkScrap(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date fromDate;
        Date toDate;
        try {
            fromDate = sdf.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(fromDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        toDate = ca.getTime();

        long startTIme = fromDate.getTime();
        long endTime = toDate.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long time = startTIme;
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        while (time <= endTime) {
            String d = sdf2.format(new Date(time));
            List<Map> mapList = oracleMapper.getReworkScrapAry(d, d);
            List<ReworkScrapAry> reworkScrapAryList = new ArrayList<>();
            for(Map map : mapList) {
                ReworkScrapAry reworkScrapAry = new ReworkScrapAry();
                reworkScrapAry.setFabDate(new Date(((Timestamp) map.get("FAB_DATE")).getTime()));
                reworkScrapAry.setPROD_ID((String) map.get("PROD_ID"));
                reworkScrapAry.setPROD_MODEL_ID((String) map.get("PROD_MODEL_ID"));
                reworkScrapAry.setEVT_CATE((String) map.get("EVT_CATE"));
                reworkScrapAry.setOPE_ID((String) map.get("OPE_ID"));
                reworkScrapAry.setROUTE_ID((String) map.get("ROUTE_ID"));

                if(map.get("QTY") == null) {
                    reworkScrapAry.setQTY(0);
                } else {
                    reworkScrapAry.setQTY(((BigDecimal) map.get("QTY")).doubleValue());
                }
                reworkScrapAryList.add(reworkScrapAry);
            }
            reworkScrapAryRepository.saveAll(reworkScrapAryList);
            time += oneDay;
        }
    }

    @Override
    public void computeAryReworkScrapAmount(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date fromDate;
        Date toDate;
        try {
            fromDate = sdf.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(fromDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        toDate = ca.getTime();

        List<ReworkScrapAry> reworkScrapAryList = reworkScrapAryRepository.findByFabDateBetween(fromDate, toDate);
        int i = reworkScrapAryList.size();
        for(ReworkScrapAry reworkScrapAry : reworkScrapAryList) {
            String m = sdf.format(reworkScrapAry.getFabDate());
            String evt_cat = reworkScrapAry.getEVT_CATE();
            String station = reworkScrapAry.getOPE_ID();
            String project = reworkScrapAry.getPROD_MODEL_ID();
            if(StringUtils.isEmpty(project)) continue;
            double qty = reworkScrapAry.getQTY();
            double d;
            //报废
            if(StringUtils.equalsIgnoreCase(evt_cat, "SCRP")) {
                d = stationCostService.getPerScrapAmountAry(m, project, station);
            } else {
                d = stationCostService.getPerReworkAmountAry(m, project, station);
            }
            reworkScrapAry.setStationAmount(d);
            reworkScrapAry.setAmount(d*qty);
            System.out.println(i--);
        }
        reworkScrapAryRepository.saveAll(reworkScrapAryList);
    }


    @Override
    public void computeReworkScrapMonth(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date fromDate;
        Date toDate;
        try {
            fromDate = sdf.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(fromDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        toDate = ca.getTime();
        ReworkScrapMonth ary = new ReworkScrapMonth();
        double d = reworkScrapAryRepository.getMpReworkScrapAmount(fromDate, toDate);
        ary.setMonth(month);
        ary.setFab("ARRAY");
        ary.setAmount(d);

        reworkScrapMonthRepository.save(ary);
    }
}
