package com.ivo.product.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.product.entity.ReworkScrapLcm1;
import com.ivo.product.entity.ReworkScrapLcm2;
import com.ivo.product.entity.ReworkScrapMonth;
import com.ivo.product.repository.ReworkScrapLcm1Repository;
import com.ivo.product.repository.ReworkScrapLcm2Repository;
import com.ivo.product.repository.ReworkScrapMonthRepository;
import com.ivo.product.service.ReworkScrapLcmService;
import com.ivo.rest.oracle.entity.OracleReworkScrapLcm;
import com.ivo.rest.oracle.mapper.OracleMapper;
import com.ivo.station.service.StationCostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ReworkScrapLcmServiceImpl implements ReworkScrapLcmService {

    @Resource
    private OracleMapper oracleMapper;

    @Resource
    private ReworkScrapLcm1Repository lcm1Repository;

    @Resource
    private ReworkScrapLcm2Repository lcm2Repository;

    @Resource
    private StationCostService stationCostService;

    @Resource
    private ReworkScrapMonthRepository reworkScrapMonthRepository;

    @Override
    public void syncReworkScrap(String month) {
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

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        syncReworkScrapLcm1(sdf2.format(fromDate), sdf2.format(toDate));
        syncReworkScrapLcm2(sdf2.format(fromDate), sdf2.format(toDate));
    }

    @Override
    public void syncReworkScrapLcm1(String fromDate, String toDate) {
        if(fromDate==null || toDate==null) return;
        //遍历每一天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long startTIme = 0;
        long endTime = 0;
        try {
            startTIme = sdf.parse(fromDate).getTime();
            endTime = sdf.parse(toDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        long oneDay = 1000 * 60 * 60 * 24;
        long time = startTIme;
        while (time <= endTime) {
            String d = sdf.format(new Date(time));
            List<OracleReworkScrapLcm> list =  oracleMapper.getReworkScrapLcm1(d, d);
            List<ReworkScrapLcm1> reworkScrapLcm1List = new ArrayList<>();
            for(OracleReworkScrapLcm o : list) {
                ReworkScrapLcm1 reworkScrapLcm1 = new ReworkScrapLcm1();
                reworkScrapLcm1.setWO_ID(o.getWO_ID());
                reworkScrapLcm1.setCR_OPE_ID(o.getCR_OPE_ID());
                reworkScrapLcm1.setEVT_CATE(o.getEVT_CATE());
                reworkScrapLcm1.setFabDate(o.getFAB_DATE());
                reworkScrapLcm1.setPROD_ID(o.getPROD_ID());
                reworkScrapLcm1.setPROD_MODEL_ID(o.getPROD_MODEL_ID());
                reworkScrapLcm1.setQTY(o.getQTY());
                reworkScrapLcm1.setSCRP_M_OPE_ID(o.getSCRP_M_OPE_ID());
                reworkScrapLcm1List.add(reworkScrapLcm1);
            }
            lcm1Repository.saveAll(reworkScrapLcm1List);
            time += oneDay;
        }
    }

    @Override
    public void syncReworkScrapLcm2(String fromDate, String toDate) {
        if(fromDate==null || toDate==null) return;
        //遍历每一天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long startTIme = 0;
        long endTime = 0;
        try {
            startTIme = sdf.parse(fromDate).getTime();
            endTime = sdf.parse(toDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        long oneDay = 1000 * 60 * 60 * 24;
        long time = startTIme;
        while (time <= endTime) {
            String d = sdf.format(new Date(time));
            System.out.println(d);
            List<OracleReworkScrapLcm> list =  oracleMapper.getReworkScrapLcm2(d, d);
            List<ReworkScrapLcm2> reworkScrapLcm2List = new ArrayList<>();
            for(OracleReworkScrapLcm o : list) {
                ReworkScrapLcm2 reworkScrapLcm2 = new ReworkScrapLcm2();
                reworkScrapLcm2.setWO_ID(o.getWO_ID());
                reworkScrapLcm2.setCR_OPE_ID(o.getCR_OPE_ID());
                reworkScrapLcm2.setEVT_CATE(o.getEVT_CATE());
                reworkScrapLcm2.setFabDate(o.getFAB_DATE());
                reworkScrapLcm2.setPROD_ID(o.getPROD_ID());
                reworkScrapLcm2.setPROD_MODEL_ID(o.getPROD_MODEL_ID());
                reworkScrapLcm2.setQTY(o.getQTY());
                reworkScrapLcm2.setSCRP_M_OPE_ID(o.getSCRP_M_OPE_ID());
                reworkScrapLcm2List.add(reworkScrapLcm2);
            }
            lcm2Repository.saveAll(reworkScrapLcm2List);
            time += oneDay;
        }
    }

    @Override
    public void syncPerReworkScrapAmountLcm(String month) {
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

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        syncPerReworkScrapAmountLcm1(fromDate, toDate);
        syncPerReworkScrapAmountLcm2(fromDate, toDate);
    }

    @Override
    public void syncPerReworkScrapAmountLcm1(Date fromDate, Date toDate) {
        List<ReworkScrapLcm1> reworkScrapLcm1List = lcm1Repository.findByFabDateBetween(fromDate, toDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for(ReworkScrapLcm1 reworkScrapLcm1 : reworkScrapLcm1List) {
            String month = sdf.format(reworkScrapLcm1.getFabDate());
            String product = reworkScrapLcm1.getPROD_ID();
            String evt_cat = reworkScrapLcm1.getEVT_CATE();
            String station = reworkScrapLcm1.getCR_OPE_ID();
            double qty = reworkScrapLcm1.getQTY();
            // 如果站点时7900要往前推站点
            if(StringUtils.equals(station, "7900")) {
                station = reworkScrapLcm1.getSCRP_M_OPE_ID();
            }
            if(StringUtils.isEmpty(station)) continue;

            double d;
            //报废
            if(StringUtils.equalsIgnoreCase(evt_cat, "SCRP")) {
                d = stationCostService.getPerScrapAmountLcm(month, product, station);
            } else {
                d = stationCostService.getPerReworkAmountLcm(month, product, station);
            }
            reworkScrapLcm1.setStationAmount(d);
            reworkScrapLcm1.setAmount(d*qty);
        }

        lcm1Repository.saveAll(reworkScrapLcm1List);
    }

    @Override
    public void syncPerReworkScrapAmountLcm2(Date fromDate, Date toDate) {
        List<ReworkScrapLcm2> reworkScrapLcm2List = lcm2Repository.findByFabDateBetween(fromDate, toDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for(ReworkScrapLcm2 reworkScrapLcm2 : reworkScrapLcm2List) {
            String month = sdf.format(reworkScrapLcm2.getFabDate());
            String product = reworkScrapLcm2.getPROD_ID();
            String evt_cat = reworkScrapLcm2.getEVT_CATE();
            String station = reworkScrapLcm2.getCR_OPE_ID();
            double qty = reworkScrapLcm2.getQTY();
            // 如果站点时7900要往前推站点
            if(StringUtils.equals(station, "7900")) {
                station = reworkScrapLcm2.getSCRP_M_OPE_ID();
            }
            if(StringUtils.isEmpty(station)) continue;

            double d;
            //报废
            if(StringUtils.equalsIgnoreCase(evt_cat, "SCRP")) {
                d = stationCostService.getPerScrapAmountLcm(month, product, station);
            } else {
                d = stationCostService.getPerReworkAmountLcm(month, product, station);
            }
            reworkScrapLcm2.setStationAmount(d);
            reworkScrapLcm2.setAmount(d*qty);
        }
        lcm2Repository.saveAll(reworkScrapLcm2List);
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
        ReworkScrapMonth lcm1 = new ReworkScrapMonth();
        ReworkScrapMonth lcm2 = new ReworkScrapMonth();

        double d1 = lcm1Repository.getMpReworkScrapAmount(fromDate, toDate);
        double d2 = lcm2Repository.getMpReworkScrapAmount(fromDate, toDate);

        lcm1.setMonth(month);
        lcm1.setFab("LCM1");
        lcm1.setAmount(d1);

        lcm2.setMonth(month);
        lcm2.setFab("LCM2");
        lcm2.setAmount(d2);
        reworkScrapMonthRepository.save(lcm1);
        reworkScrapMonthRepository.save(lcm2);
    }
}
