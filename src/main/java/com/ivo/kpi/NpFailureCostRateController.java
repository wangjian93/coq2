package com.ivo.kpi;

import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.report.entity.InLossAmount;
import com.ivo.coq.report.repository.InLossAmountRepository;
import org.aspectj.apache.bcel.generic.InstructionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/npFailureCostRate")
public class NpFailureCostRateController {

    @Resource
    private InLossAmountRepository inLossAmountRepository;

    @RequestMapping("/getRate")
    public Result getRate(String fromMonth, String toMonth) throws ParseException {
        fromMonth = fromMonth.replace("-", "");
        toMonth = toMonth.replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");

        //从202001月份开始计算累加
        fromMonth = "202001";

        Date fromDate = sdf.parse(fromMonth);
        Date toDate = sdf.parse(toMonth);
        Calendar min = Calendar.getInstance();
        min.setTime(fromDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        Calendar max = Calendar.getInstance();
        max.setTime(toDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        Calendar curr = min;
        List<String> monthList = new ArrayList<>();
        while (curr.before(max)) {
            monthList.add(sdf2.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        //计算季度
        List<String > seasonList = new ArrayList<>();
        for(String month : monthList) {
            String season = getSeason(sdf2.parse(month));
            if(seasonList.contains(season)) continue;
            else seasonList.add(season);
        }
        Map<String, Map> map_season = new HashMap();
        for(String season : seasonList) {
            Map<String, Double> subMap = new HashMap<>();
            subMap.put("in_loss_cost", 0D);
            subMap.put("out_loss_cost", 0D);
            subMap.put("identity_cost", 0D);
            subMap.put("prevention_cost", 0D);
            map_season.put(season, subMap);
        }

        List<Map> map_month = inLossAmountRepository.getNpFailureCostRate(fromMonth, toMonth);
        for(Map map : map_month) {
            String month = (String) map.get("month");
            double in_loss_cost = (Double)map.get("in_loss_cost");
            double out_loss_cost = (Double)map.get("out_loss_cost");
            double identity_cost = (Double)map.get("identity_cost");
            double prevention_cost = (Double)map.get("prevention_cost");
            String season = getSeason(sdf2.parse(month));
            Map<String, Double> subMap = map_season.get(season);
            subMap.put("in_loss_cost", subMap.get("in_loss_cost") + in_loss_cost);
            subMap.put("out_loss_cost", subMap.get("out_loss_cost") + out_loss_cost);
            subMap.put("identity_cost", subMap.get("identity_cost") + identity_cost);
            subMap.put("prevention_cost", subMap.get("prevention_cost") + prevention_cost);
        }


        List<Map> mapList = new ArrayList<>();
        for(String season : seasonList) {
            Map map = new HashMap();
            map.put("month", season);
            map.putAll(map_season.get(season));
            mapList.add(map);
        }
        return ResultUtil.success(mapList);
    }

    /**
     * 获取季度
     * @param date
     * @return
     */
    private static String getSeason(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        String season;
        if(month>=1&&month<=3){
            season=year+"Q1";
        }
        else if(month>=4&&month<=6){
            season=year+"Q2";
        }
        else  if(month>=7&&month<=9){
            season=year+"Q3";
        }
        else{
            season=year+"Q4";
        }
        return season;
    }

    @RequestMapping("/getDetail")
    public Result getNpFailureCostRateDetail(String month) {
        String season = month;
        String[] ars = season.split("Q");
        String pre = ars[0];
        String s = ars[1];
        List<String> monthList = new ArrayList<>();
        if(s.equals("1")) {
            monthList.add(pre + "01");
            monthList.add(pre + "02");
            monthList.add(pre + "03");
        } else if(s.equals("2")) {
            monthList.add(pre + "04");
            monthList.add(pre + "05");
            monthList.add(pre + "06");
        } else if(s.equals("3")) {
            monthList.add(pre + "07");
            monthList.add(pre + "08");
            monthList.add(pre + "09");
        } else if(s.equals("4")) {
            monthList.add(pre + "10");
            monthList.add(pre + "11");
            monthList.add(pre + "12");
        }

        List<Map> mapList = inLossAmountRepository.getNpFailureCostRateDetail(monthList);
        List<Map> list = new ArrayList<>();
        for(Map map : mapList) {
            if(((double)map.get("rate")>0)) list.add(map);
        }
        return ResultUtil.successPage(list);
    }
}
