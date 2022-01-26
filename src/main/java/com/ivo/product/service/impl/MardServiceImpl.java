package com.ivo.product.service.impl;

import com.ivo.product.entity.Mard;
import com.ivo.product.entity.MardMonth;
import com.ivo.product.repository.MardMonthRepository;
import com.ivo.product.repository.MardRepository;
import com.ivo.product.service.MardService;
import com.ivo.rest.oracle.mapper.OracleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class MardServiceImpl implements MardService {

    @Resource
    private OracleMapper oracleMapper;

    @Resource
    private MardRepository mardRepository;

    @Resource
    private MardMonthRepository mardMonthRepository;

    @Override
    public void syncMard(String fromDate, String toDate) {
        List<Map> list = oracleMapper.getMard(fromDate, toDate);
        List<Mard> mardList = new ArrayList<>();
        for(Map map : list) {
            Mard mard = new Mard();
            mard.setWERKS((String) map.get("WERKS"));
            mard.setLGORT((String) map.get("LGORT"));
            mard.setMATNR((String) map.get("MATNR"));
            mard.setERSDA(new Date(((Timestamp) map.get("BLDAT")).getTime())); //BLDAT
            mard.setLABST((double) map.get("MENGE"));//MENGE

            mard.setAUFNR((String) map.get("AUFNR"));
            mard.setCHARG((String) map.get("CHARG"));
            mardList.add(mard);
        }
        mardRepository.saveAll(mardList);
    }

    @Override
    public void syncMard(String month) {
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
        syncMard(sdf2.format(fromDate), sdf2.format(toDate));
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
        List<Mard> mardList = mardRepository.findByERSDABetween(fromDate, toDate);
        for(Mard mard : mardList) {
            mard.setPrice(oracleMapper.getMaterialPrice(mard.getMATNR()));
        }
        mardRepository.saveAll(mardList);
    }


    @Override
    public List<Mard> getMardLcm1(Date fromDate, Date toDate) {
        //工厂3000 3500
        List<String> werks = new ArrayList<>();
        werks.add("3000");
        werks.add("3500");
        return mardRepository.findByWERKSInAndERSDABetween(werks, fromDate, toDate);
    }

    @Override
    public List<Mard> getMardLcm2(Date fromDate, Date toDate) {
        //工厂1000 5000
        List<String> werks = new ArrayList<>();
        werks.add("1000");
        werks.add("5000");
        return mardRepository.findByWERKSInAndERSDABetween(werks, fromDate, toDate);
    }

    @Override
    public void computeMardMonth(String month) {
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
        List<Mard> lcm1List = getMardLcm1(fromDate, toDate);
        double total_lcm1 = 0;
        for(Mard mard : lcm1List) {
            if(StringUtils.startsWith(mard.getMATNR(), "14")) continue;
            if(mard.getAUFNR() != null && mard.getAUFNR().length()>3 && StringUtils.equalsAny( mard.getAUFNR().substring(2,3), "1", "8")) {
            } else {
                continue;
            }

            total_lcm1 += mard.getLABST()*mard.getPrice();
        }

        List<Mard> lcm2List = getMardLcm2(fromDate, toDate);
        double total_lcm2 = 0;
        for(Mard mard : lcm2List) {
            if(StringUtils.startsWith(mard.getMATNR(), "14")) continue;
            if(mard.getAUFNR() != null && mard.getAUFNR().length()>3 && StringUtils.equalsAny( mard.getAUFNR().substring(2,3), "1", "8")) {

            } else {
                continue;
            }


            total_lcm2 += mard.getLABST()*mard.getPrice();
        }

        MardMonth mardMonth1 = new MardMonth();
        mardMonth1.setFab("LCM1");
        mardMonth1.setMonth(month);
        mardMonth1.setAmount(total_lcm1);

        MardMonth mardMonth2 = new MardMonth();
        mardMonth2.setFab("LCM2");
        mardMonth2.setMonth(month);
        mardMonth2.setAmount(total_lcm2);

        mardMonthRepository.save(mardMonth1);
        mardMonthRepository.save(mardMonth2);
    }
}
