package com.ivo.product.service.impl;

import com.ivo.product.entity.WhScrap;
import com.ivo.product.entity.WhScrapMonth;
import com.ivo.product.repository.WhScrapMonthRepository;
import com.ivo.product.repository.WhScrapRepository;
import com.ivo.product.service.WhScrapService;
import com.ivo.rest.eifdb.mapper.EifMapper;
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
public class WhScrapServiceImpl implements WhScrapService {

    @Resource
    private EifMapper eifMapper;

    @Resource
    private WhScrapRepository whScrapRepository;

    @Resource
    private WhScrapMonthRepository whScrapMonthRepository;

    @Override
    public void syncWhScrap() {
        List<Map> list = eifMapper.getWhScrap();
        List<WhScrap> whScrapList = new ArrayList<>();
        for(Map map : list) {
            WhScrap whScrap = new WhScrap();
            whScrap.setFab((String) map.get("fab"));
            whScrap.setDateOfOrder((Date) map.get("dateOfOrder"));
            whScrap.setTrackingNumber((String) map.get("trackingNumber"));
            whScrap.setCostCenter_fk((String) map.get("costCenter_fk"));
            whScrap.setUserDepartment_fk((String) map.get("userDepartment_fk"));
            whScrap.setScrapMoneyCny((double) map.get("scrapMoneyCny"));
            whScrapList.add(whScrap);
        }
        whScrapRepository.saveAll(whScrapList);
    }

    @Override
    public void computeWhScrapMonth(String month) {
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
        List<WhScrap> lcm1List = whScrapRepository.findByFabAndDateOfOrderBetween("LCM1", fromDate, toDate);
        List<WhScrap> lcm2List = whScrapRepository.findByFabAndDateOfOrderBetween("LCM2", fromDate, toDate);
        List<WhScrap> cellList = whScrapRepository.findByFabAndDateOfOrderBetween("CELL", fromDate, toDate);
        List<WhScrap> arrayList = whScrapRepository.findByFabAndDateOfOrderBetween("ARRAY", fromDate, toDate);
        double total_lcm1 = 0;
        double total_lcm2 = 0;
        double total_cell = 0;
        double total_array = 0;
        for(WhScrap whScrap : lcm1List) {
            total_lcm1 += whScrap.getScrapMoneyCny();
        }
        for(WhScrap whScrap : lcm2List) {
            total_lcm2 += whScrap.getScrapMoneyCny();
        }
        for(WhScrap whScrap : cellList) {
            total_cell += whScrap.getScrapMoneyCny();
        }
        for(WhScrap whScrap : arrayList) {
            total_array += whScrap.getScrapMoneyCny();
        }

        WhScrapMonth lcm1 = new WhScrapMonth();
        lcm1.setMonth(month);
        lcm1.setFab("LCM1");
        lcm1.setAmount(total_lcm1);

        WhScrapMonth lcm2 = new WhScrapMonth();
        lcm2.setMonth(month);
        lcm2.setFab("LCM2");
        lcm2.setAmount(total_lcm2);

        WhScrapMonth cell = new WhScrapMonth();
        cell.setMonth(month);
        cell.setFab("CELL");
        cell.setAmount(total_cell);

        WhScrapMonth array = new WhScrapMonth();
        array.setMonth(month);
        array.setFab("ARRAY");
        array.setAmount(total_array);

        whScrapMonthRepository.save(lcm1);
        whScrapMonthRepository.save(lcm2);
        whScrapMonthRepository.save(cell);
        whScrapMonthRepository.save(array);
    }
}
