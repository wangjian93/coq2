package com.ivo.product.service.impl;

import com.ivo.product.entity.WoClose;
import com.ivo.product.entity.WoCloseMonth;
import com.ivo.product.repository.WoCloseMonthRepository;
import com.ivo.product.repository.WoCloseRepository;
import com.ivo.product.service.WoCloseService;
import com.ivo.rest.oracle.mapper.OracleMapper;
import oracle.sql.TIMESTAMP;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class WoCloseServiceImpl implements WoCloseService {

    @Resource
    private OracleMapper oracleMapper;

    @Resource
    private WoCloseRepository woCloseRepository;

    @Resource
    private WoCloseMonthRepository woCloseMonthRepository;

    @Override
    public void syncWoClose(String fromDate, String toDate) {
        List<Map> list = oracleMapper.getLcmWoClose(fromDate, toDate);
        List<WoClose> woCloseList = new ArrayList<>();
        for(Map map : list) {
            WoClose woClose = new WoClose();
            woClose.setWERKS((String) map.get("WERKS"));
            try {
                woClose.setCloseDate(((TIMESTAMP) map.get("CLOSE_DATE")).dateValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            woClose.setAUFNR((String) map.get("AUFNR"));
            woClose.setAUFNR_T((String) map.get("AUFNR_T"));
            woClose.setMTRL_ID((String) map.get("MTRL_ID"));
            woClose.setPROJECT((String) map.get("PROJECT"));
            woClose.setMATNR((String) map.get("MATNR"));
            if(map.get("QTY") == null) {
                woClose.setQTY(0);
            } else {
                woClose.setQTY(((BigDecimal) map.get("QTY")).doubleValue());
            }
            woCloseList.add(woClose);
        }
        woCloseRepository.saveAll(woCloseList);
    }

    @Override
    public void syncWoClose(String month) {
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
        syncWoClose(sdf2.format(fromDate), sdf2.format(toDate));
    }

    @Override
    public void syncMatnrPrice(String month) {
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

        List<WoClose> list = woCloseRepository.findByCloseDateBetween(fromDate, toDate);
        for(WoClose woClose : list) {
            woClose.setPrice(oracleMapper.getMaterialPrice(woClose.getMATNR()));
        }
        woCloseRepository.saveAll(list);
    }

    @Override
    public List<WoClose> getWoCloseLcm1(Date fromDate, Date toDate) {
        //工厂3000
        return woCloseRepository.findByWERKSAndCloseDateBetween("3000", fromDate, toDate);
    }

    @Override
    public List<WoClose> getWoCloseLcm2(Date fromDate, Date toDate) {
        //工厂1000
        return woCloseRepository.findByWERKSAndCloseDateBetween("1000", fromDate, toDate);
    }

    @Override
    public void computeWoCloseMonth(String month) {
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
        List<WoClose> lcm1List = getWoCloseLcm1(fromDate, toDate);
        double total_lcm1 = 0;
        for(WoClose woClose : lcm1List) {
            if(woClose.getQTY()>0) {
                total_lcm1 += woClose.getQTY()*woClose.getPrice();
            }
        }

        List<WoClose> lcm2List = getWoCloseLcm2(fromDate, toDate);
        double total_lcm2 = 0;
        for(WoClose woClose : lcm2List) {
            if(woClose.getQTY()>0) {
                total_lcm2 += woClose.getQTY()*woClose.getPrice();
            }
        }

        WoCloseMonth woCloseMonth1 = new WoCloseMonth();
        woCloseMonth1.setFab("LCM1");
        woCloseMonth1.setMonth(month);
        woCloseMonth1.setAmount(total_lcm1);

        WoCloseMonth woCloseMonth2 = new WoCloseMonth();
        woCloseMonth2.setFab("LCM2");
        woCloseMonth2.setMonth(month);
        woCloseMonth2.setAmount(total_lcm2);

        woCloseMonthRepository.save(woCloseMonth1);
        woCloseMonthRepository.save(woCloseMonth2);
    }
}
