package com.ivo.station.service.impl;

import com.ivo.rest.oracle.mapper.OracleMapper;
import com.ivo.station.entity.StationCycleTimeAry;
import com.ivo.station.entity.StationCycleTimeCell;
import com.ivo.station.repository.StationCycleTimeCellRepository;
import com.ivo.station.service.EqWorkCenterService;
import com.ivo.station.service.StationCycleTimeCellService;
import com.ivo.station.service.WorkRateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class StationCycleTimeCellServiceImpl implements StationCycleTimeCellService {

    @Resource
    private StationCycleTimeCellRepository stationCycleTimeCellRepository;

    @Resource
    private OracleMapper oracleMapper;

    @Resource
    private EqWorkCenterService eqWorkCenterService;

    @Resource
    private WorkRateService workRateService;

    @Override
    public void syncStationCycleTimeCell(String month) {
        //数据已计算过不再重复计算
        if(stationCycleTimeCellRepository.countByMonth(month)>0) return;
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

        syncStationCycleTimeCell(fromDate, toDate, month);
        computeCellStationAmount(month);
    }

    @Override
    public void syncStationCycleTimeCell(Date fromDate, Date toDate, String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map> mapList = oracleMapper.getStationCycleTimeCell(sdf.format(fromDate), sdf.format(toDate));
        List<StationCycleTimeCell> stationCycleTimeCellList = new ArrayList<>();
        for(Map map : mapList) {
            StationCycleTimeCell stationCycleTimeCell = new StationCycleTimeCell();
            stationCycleTimeCell.setMonth(month);
            stationCycleTimeCell.setProject((String) map.get("MODEL_ID"));
            stationCycleTimeCell.setProdId((String) map.get("PROD_ID"));
            stationCycleTimeCell.setGlass_thin((String) map.get("GLASS_THIN"));
            stationCycleTimeCell.setStation((String) map.get("OPE_ID"));
            stationCycleTimeCell.setStationDes((String) map.get("OPE_NAME"));
            stationCycleTimeCell.setEqptId((String) map.get("EQPT_ID"));
            stationCycleTimeCell.setM_cnt(((BigDecimal) map.get("M_CNT")).doubleValue());
            stationCycleTimeCell.setR_cnt(((BigDecimal) map.get("R_CNT")).doubleValue());
            stationCycleTimeCell.setS_cnt(((BigDecimal) map.get("S_CNT")).doubleValue());
            stationCycleTimeCell.setM_cnt_s(((BigDecimal) map.get("M_CNT_S")).doubleValue());
            stationCycleTimeCell.setR_cnt_s(((BigDecimal) map.get("R_CNT_S")).doubleValue());
            stationCycleTimeCell.setS_cnt_s(((BigDecimal) map.get("S_CNT_S")).doubleValue());
            stationCycleTimeCell.setM_time(((BigDecimal) map.get("M_TIME")).doubleValue());
            stationCycleTimeCell.setR_time(((BigDecimal) map.get("R_TIME")).doubleValue());
            stationCycleTimeCell.setS_time(((BigDecimal) map.get("S_TIME")).doubleValue());
            stationCycleTimeCellList.add(stationCycleTimeCell);
        }
        stationCycleTimeCellRepository.saveAll(stationCycleTimeCellList);
    }

    @Override
    public void computeCellStationAmount(String month) {
        List<StationCycleTimeCell> stationCycleTimeCellList = stationCycleTimeCellRepository.findByMonth(month);
        for(StationCycleTimeCell stationCycleTimeCell : stationCycleTimeCellList) {
            String eq = stationCycleTimeCell.getEqptId();
            String costCenter = eqWorkCenterService.getCostCenterByEq(eq, "CELL");
            if(StringUtils.isEmpty(costCenter)) {
                stationCycleTimeCell.setMemo("机台未匹配到成本中心");
                continue;
            }
            stationCycleTimeCell.setCostCenter(costCenter);

            double workRate = workRateService.getTotalPrice(costCenter);
            if(workRate <= 0) {
                stationCycleTimeCell.setMemo("未获取到单位费率");
                continue;
            }
            stationCycleTimeCell.setWorkRate(workRate);

            // 生产费用=主站点对应的主站点CT（不含等待）*24*60*单位费率+检查站点对应的检查站点CT（不含等待）*24*60*单位费率
            // 重工费用=Rework站点对应的Rework站点CT（不含等待）*24*60*单位费率
            double amount = stationCycleTimeCell.getM_time()*24*60*workRate + stationCycleTimeCell.getS_time()*24*60*workRate;
            double reworkAmount = stationCycleTimeCell.getS_time()*24*60*workRate;
            stationCycleTimeCell.setAmount(amount);
            stationCycleTimeCell.setReworkAmount(reworkAmount);
        }
        stationCycleTimeCellRepository.saveAll(stationCycleTimeCellList);
    }

    @Override
    public Double getPerProductAmountCell(String month, String project, String prod_id) {
        //如何当月没有维护数据查取最近一个月的数据
        if(stationCycleTimeCellRepository.countByMonthAndProject(month, project)==0) {
            month = stationCycleTimeCellRepository.getLastMonth(project);
        }
        if(month==null) return null;
        //分类栏位为PFCD的第八码
        prod_id = prod_id.substring(7, 8);
        Double d = stationCycleTimeCellRepository.getPerProductAmountCell(month, project, prod_id);

        return stationCycleTimeCellRepository.getPerProductAmountCell(month, project, prod_id);
    }

    @Override
    public Double getPerScrapAmountCell(String month, String project, String station, String prod_id) {
        //如何当月没有维护数据查取最近一个月的数据
        if(stationCycleTimeCellRepository.countByMonthAndProject(month, project)==0) {
            month = stationCycleTimeCellRepository.getLastMonth(project);
        }
        if(month==null) return null;
        //分类栏位为PFCD的第八码
        prod_id = prod_id.substring(7, 8);

        StationCycleTimeCell stationCycleTimeCell = stationCycleTimeCellRepository.findFirstByMonthAndProjectAndStationLikeAndProdId(month, project, station+'%', prod_id);
        // 匹配不到站点时，匹配前三码，还没有然后再前两码
//        if(stationCycleTimeCell == null) {
//            if(station.length()>3) {
//                station = station.substring(0, 3);
//                stationCycleTimeCell = stationCycleTimeCellRepository.findFirstByMonthAndProjectAndStationLikeAndProdId(month, project, station+'%', prod_id);
//                if(stationCycleTimeCell == null) {
//                    if(station.length()>2) {
//                        station = station.substring(0, 2);
//                        stationCycleTimeCell = stationCycleTimeCellRepository.findFirstByMonthAndProjectAndStationLikeAndProdId(month, project, station+'%', prod_id);
//                    }
//                }
//            }
//        }
        return stationCycleTimeCell==null ? null : stationCycleTimeCell.getAmount();
    }

    @Override
    public Double getPerReworkAmountCell(String month, String project, String station, String prod_id) {
        //如何当月没有维护数据查取最近一个月的数据
        if(stationCycleTimeCellRepository.countByMonthAndProject(month, project)==0) {
            month = stationCycleTimeCellRepository.getLastMonth(project);
        }
        if(month==null) return null;
        //分类栏位为PFCD的第八码
        prod_id = prod_id.substring(7, 8);

        return stationCycleTimeCellRepository.getPerReworkAmountCell(month, project, station, prod_id);
    }
}
