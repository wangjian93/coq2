package com.ivo.product.service.impl;

import com.ivo.product.entity.ReworkScrapCell;
import com.ivo.product.entity.ReworkScrapMonth;
import com.ivo.product.repository.ReworkScrapCellRepository;
import com.ivo.product.repository.ReworkScrapMonthRepository;
import com.ivo.product.service.ReworkScrapCellService;
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
public class ReworkScrapCellServiceImpl implements ReworkScrapCellService {

    @Resource
    private OracleMapper oracleMapper;

    @Resource
    private ReworkScrapCellRepository reworkScrapCellRepository;

    @Resource
    private StationCostService stationCostService;

    @Resource
    private ReworkScrapMonthRepository reworkScrapMonthRepository;

    @Override
    public void syncCellReworkScrap(String month) {
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
            List<Map> mapList = oracleMapper.getReworkScrapCell(d, d);
            List<ReworkScrapCell> reworkScrapCellList = new ArrayList<>();
            for(Map map : mapList) {
                ReworkScrapCell reworkScrapCell = new ReworkScrapCell();
                reworkScrapCell.setFabDate(new Date(((Timestamp) map.get("FAB_DATE")).getTime()));
                reworkScrapCell.setPROD_ID((String) map.get("PROD_ID"));
                reworkScrapCell.setARY_PROD_ID((String) map.get("ARY_PROD_ID"));
                reworkScrapCell.setPRODUCT_TYP((String) map.get("PRODUCT_TYP"));
                reworkScrapCell.setEVT_CATE((String) map.get("EVT_CATE"));
                reworkScrapCell.setPROD_MODEL_ID((String) map.get("PROD_MODEL_ID"));
                reworkScrapCell.setNX_OPE_ID((String) map.get("NX_OPE_ID"));
                if(map.get("QTY") == null) {
                    reworkScrapCell.setQTY(0);
                } else {
                    reworkScrapCell.setQTY(((BigDecimal) map.get("QTY")).doubleValue());
                }
                reworkScrapCellList.add(reworkScrapCell);
            }
            reworkScrapCellRepository.saveAll(reworkScrapCellList);
            time += oneDay;
        }
    }

    @Override
    public void computeCellReworkScrapAmount(String month) {
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

        List<ReworkScrapCell> reworkScrapCellList = reworkScrapCellRepository.findByFabDateBetween(fromDate, toDate);
        for(ReworkScrapCell reworkScrapCell : reworkScrapCellList) {
            String m = sdf.format(reworkScrapCell.getFabDate());
            String prod_id = reworkScrapCell.getPROD_ID();
            String evt_cat = reworkScrapCell.getEVT_CATE();
            String station = reworkScrapCell.getNX_OPE_ID();
            String project = reworkScrapCell.getPROD_MODEL_ID();
            double qty = reworkScrapCell.getQTY();
            double d;
            //报废
            if(StringUtils.equalsIgnoreCase(evt_cat, "SCRP")) {
                d = stationCostService.getPerScrapAmountCell(m, project, station, prod_id);
            } else {
                d = stationCostService.getPerReworkAmountCell(m, project, station, prod_id);
            }
            reworkScrapCell.setStationAmount(d);
            reworkScrapCell.setAmount(d*qty);
        }
        reworkScrapCellRepository.saveAll(reworkScrapCellList);
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
        Double d = reworkScrapCellRepository.getMpReworkScrapAmount(fromDate, toDate);
        if (d==null) d=0d;
        ary.setMonth(month);
        ary.setFab("CELL");
        ary.setAmount(d);
        reworkScrapMonthRepository.save(ary);
    }
}
