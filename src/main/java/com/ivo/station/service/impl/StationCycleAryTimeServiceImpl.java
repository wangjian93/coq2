package com.ivo.station.service.impl;

import com.ivo.rest.oracle.mapper.OracleMapper;
import com.ivo.station.entity.StationCycleTimeAry;
import com.ivo.station.entity.StationCycleTimeAryTemp;
import com.ivo.station.repository.StationCycleTimeAryRepository;
import com.ivo.station.repository.StationCycleTimeAryTempRepository;
import com.ivo.station.service.EqWorkCenterService;
import com.ivo.station.service.StationCycleTimeAryService;
import com.ivo.station.service.WorkRateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class StationCycleAryTimeServiceImpl implements StationCycleTimeAryService {

    @Resource
    private OracleMapper oracleMapper;

    @Resource
    private StationCycleTimeAryTempRepository stationCycleTimeAryTempRepository;

    @Resource
    private StationCycleTimeAryRepository stationCycleTimeAryRepository;

    @Resource
    private EqWorkCenterService eqWorkCenterService;

    @Resource
    private WorkRateService workRateService;


    @Override
    public void syncStationCycleTime(Date fromDate, Date toDate) {
        stationCycleTimeAryTempRepository.deleteAll();
        long startTIme = fromDate.getTime();
        long endTime = toDate.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long time = startTIme;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (time <= endTime) {
            String d = sdf.format(new Date(time));
            List<StationCycleTimeAryTemp> list = oracleMapper.getStationCycleTimeAry(d, d);
            stationCycleTimeAryTempRepository.saveAll(list);
            time += oneDay;
        }
    }

    @Override
    public void sumStationCycleTime(String month) {
        List<Map> mapList = stationCycleTimeAryTempRepository.sumStationCycleTimeAry();
        if(mapList.size()==0) return;

        List<StationCycleTimeAry> stationCycleTimeAryList = new ArrayList<>();
        for(Map map : mapList) {
            StationCycleTimeAry stationCycleTimeAry = new StationCycleTimeAry();
            stationCycleTimeAry.setMonth(month);
            stationCycleTimeAry.setProject((String) map.get("PROD_MODEL_ID"));
            stationCycleTimeAry.setStation((String) map.get("CR_OPE_ID"));
            stationCycleTimeAry.setStationDes((String) map.get("OPE_NAME"));
            stationCycleTimeAry.setEqptId((String) map.get("EQPT_ID"));
            stationCycleTimeAry.setPep_cnt((String) map.get("PEP_CNT"));
            stationCycleTimeAry.setSht_cnt((double) map.get("SHT_CNT"));
            stationCycleTimeAry.setB_cnt((double) map.get("B_CNT"));
            stationCycleTimeAry.setR_cnt((double) map.get("R_CNT"));
            stationCycleTimeAry.setS_cnt((double) map.get("S_CNT"));
            stationCycleTimeAry.setProc_cnt((double) map.get("M_CNT"));
            stationCycleTimeAry.setB_time((double) map.get("B_TIME"));
            stationCycleTimeAry.setR_time((double) map.get("R_TIME"));
            stationCycleTimeAry.setS_time((double) map.get("S_TIME"));
            stationCycleTimeAry.setM_time((double) map.get("M_TIME"));
            stationCycleTimeAryList.add(stationCycleTimeAry);
        }
        stationCycleTimeAryRepository.saveAll(stationCycleTimeAryList);
    }

    @Override
    public void syncStationCycleTime(String month) {
        //数据已计算过不再重复计算
        if(stationCycleTimeAryRepository.countByMonth(month)>0) return;
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

        //把当月的MDC ary cycle time数据按天同步出来至临时表中
        syncStationCycleTime(fromDate, toDate);
        //合计
        sumStationCycleTime(month);
        //计算单片经过站点费用
        computeAryStationAmount(month);
    }

    @Override
    public void computeAryStationAmount(String month) {
        List<StationCycleTimeAry> stationCycleTimeAryList = stationCycleTimeAryRepository.findByMonth(month);
        for(StationCycleTimeAry stationCycleTimeAry : stationCycleTimeAryList) {
            String eq = stationCycleTimeAry.getEqptId();
            String costCenter = eqWorkCenterService.getCostCenterByEq(eq, "ARRAY");
            if(StringUtils.isEmpty(costCenter)) {
                stationCycleTimeAry.setMemo("机台未匹配到成本中心");
                continue;
            }
            stationCycleTimeAry.setCostCenter(costCenter);

            double workRate = workRateService.getTotalPrice(costCenter);
            if(workRate <= 0) {
                stationCycleTimeAry.setMemo("未获取到单位费率");
                continue;
            }
            stationCycleTimeAry.setWorkRate(workRate);

            // 生产费用/报废费用 = S_CNT数量对应的S财务成本工时*60*单位费率+M_CNT数量对应的M财务成本工时*60*单位费率
            // 重工费用 = R_CNT数量对应的R财务成本工时*60*单位费率+B_CNT数量对应的B财务成本工时*60*单位费率
            double amount = stationCycleTimeAry.getS_time()*60*workRate + stationCycleTimeAry.getM_time()*60*workRate;
            double reworkAmount = stationCycleTimeAry.getR_time()*60*workRate + stationCycleTimeAry.getB_time()*60*workRate;
            stationCycleTimeAry.setAmount(amount);
            stationCycleTimeAry.setReworkAmount(reworkAmount);
        }
        stationCycleTimeAryRepository.saveAll(stationCycleTimeAryList);
    }

    @Override
    public Double getPerProductAmountAry(String month, String project) {
        //如何当月没有维护数据查取最近一个月的数据
        if(stationCycleTimeAryRepository.countByMonthAndProject(month, project)==0) {
            month = stationCycleTimeAryRepository.getLastMonth(project);
        }
        if (month==null) return null;
        return stationCycleTimeAryRepository.getPerProductAmountAry(month, project);
    }

    @Override
    public Double getPerScrapAmount(String month, String project, String station) {
        //如何当月没有维护数据查取最近一个月的数据
        if(stationCycleTimeAryRepository.countByMonthAndProject(month, project)==0) {
            month = stationCycleTimeAryRepository.getLastMonth(project);
        }
        if (month==null) return null;
        StationCycleTimeAry stationCycleTimeAry = stationCycleTimeAryRepository.findFirstByMonthAndProjectAndStationLike(month, project, station+'%');

        // 匹配不到站点时，匹配前三码，还没有然后再前两码
//        if(stationCycleTimeAry == null) {
//            if(station.length()>3) {
//                station = station.substring(0, 3);
//                stationCycleTimeAry = stationCycleTimeAryRepository.findFirstByMonthAndProjectAndStationLike(month, project, station+'%');
//                if(stationCycleTimeAry == null) {
//                    if(station.length()>2) {
//                        station = station.substring(0, 2);
//                        stationCycleTimeAry = stationCycleTimeAryRepository.findFirstByMonthAndProjectAndStationLike(month, project, station+'%');
//                    }
//                }
//            }
//        }
        return stationCycleTimeAry==null ? null : stationCycleTimeAry.getAmount();
    }

    @Override
    public Double getPerReworkAmountAry(String month, String project, String station) {
        //如何当月没有维护数据查取最近一个月的数据
        if(stationCycleTimeAryRepository.countByMonthAndProject(month, project)==0) {
            month = stationCycleTimeAryRepository.getLastMonth(project);
        }
        if (month==null) return null;
        return stationCycleTimeAryRepository.getPerReworkAmountAry(month, project, station);
    }
}
