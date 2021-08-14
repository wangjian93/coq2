package com.ivo.kpi;

import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.report.repository.InLossAmountRepository;
import com.ivo.coq.report.service.WbRatioService;
import com.ivo.product.entity.Turnover;
import com.ivo.product.repository.TurnoverRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/turnoverRatio")
public class TurnoverRatioController {

    @Resource
    private InLossAmountRepository inLossAmountRepository;

    @Resource
    private WbRatioService wbRatioService;

    @Resource
    private TurnoverRepository turnoverRepository;

    @RequestMapping("/getRate")
    public Result getOutFailureCostRate(String fromMonth, String toMonth) throws ParseException {
        fromMonth = fromMonth.replace("-", "");
        toMonth = toMonth.replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");

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

        String[] fabs = new String[] {"ARRAY", "CELL", "LCM1", "LCM2"};
        Map<String, Map> map_month = new HashMap<>();
        for(String month : monthList) {
            Map<String, Double> subMap = new HashMap<>();
            subMap.put("ARRAY内损", 0D);
            subMap.put("CELL内损", 0D);
            subMap.put("LCM1内损", 0D);
            subMap.put("LCM2内损", 0D);
            subMap.put("LCM外损", 0D);
            subMap.put("CELL外损", 0D);
            subMap.put("新产品费用", 0D);
            map_month.put(month, subMap);
        }

        //内损
        List<Map> in_list = inLossAmountRepository.getRate(fromMonth, toMonth, fabs);
        for(Map map : in_list) {
            String month = (String) map.get("MONTH");
            System.out.println(month);
            if(month.equals("202101")) {
                month = month+"";
            }
            String fab = (String) map.get("FAB");
            double amount;
            if(StringUtils.startsWith(fab, "LCM")) {
                amount = (Double)map.get("报废损失外包费用") + (Double)map.get("重工报废费用") + (Double)map.get("超耗费用") + (Double)map.get("入库费用");
            } else {
                amount = (Double)map.get("内损费用") + (Double)map.get("报废损失外包费用") + (Double)map.get("重工报废费用");
            }
            Map<String, Double> subMap = map_month.get(month);
            String key;
            if(fab.equals("ARRAY")) {
                key = "ARRAY内损";
            } else if(fab.equals("CELL")) {
                key = "CELL内损";
            } else if(fab.equals("LCM1")) {
                key = "LCM1内损";
            } else if(fab.equals("LCM2")) {
                key = "LCM2内损";
            } else {
                continue;
            }
            subMap.put(key, amount);
        }

        //外损
        Map out_cell = wbRatioService.getWbRatio(monthList, "WB_CEL");
        Map out_lcm = wbRatioService.getWbRatio(monthList, "WB_LCM");
        for(String month : monthList) {
            Map map = (Map) out_cell.get(month);
            if(map == null) continue;
            double b_trip = (double) map.get("b_trip");
            double scrap = (double) map.get("scrap");
            double oba = (double) map.get("oba");
            double amount = b_trip + scrap + oba;
            Map<String, Double> subMap = map_month.get(month);
            String key = "CELL外损";
            subMap.put(key, amount);
        }
        for(String month : monthList) {
            Map map = (Map) out_lcm.get(month);
            if(map == null) continue;
            double b_trip = (double) map.get("b_trip");
            double scrap = (double) map.get("scrap");
            double oba = (double) map.get("oba");
            double amount = b_trip + scrap + oba;
            Map<String, Double> subMap = map_month.get(month);
            String key = "LCM外损";
            subMap.put(key, amount);
        }

        //新产品费用
        List<Map> np_list = inLossAmountRepository.getNpFailureCostRate(fromMonth, toMonth);
        for(Map map : np_list) {
            String month = (String) map.get("month");
            double amount = (Double)map.get("prevention_cost") + (Double)map.get("identity_cost") + (Double)map.get("in_loss_cost") + (Double)map.get("out_loss_cost");
            Map<String, Double> subMap = map_month.get(month);
            String key = "新产品费用";
            subMap.put(key, amount);
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
            subMap.put("ARRAY内损", 0D);
            subMap.put("CELL内损", 0D);
            subMap.put("LCM1内损", 0D);
            subMap.put("LCM2内损", 0D);
            subMap.put("LCM外损", 0D);
            subMap.put("CELL外损", 0D);
            subMap.put("新产品费用", 0D);
            subMap.put("营业额", 0D);
            map_season.put(season, subMap);
        }

        for(String month : map_month.keySet()) {
            String season = getSeason(sdf2.parse(month));
            Map<String, Double> subMap = map_season.get(season);
            Map<String, Double> map = map_month.get(month);
            subMap.put("ARRAY内损", subMap.get("ARRAY内损") + map.get("ARRAY内损"));
            subMap.put("CELL内损", subMap.get("CELL内损") + map.get("CELL内损"));
            subMap.put("LCM1内损", subMap.get("LCM1内损") + map.get("LCM1内损"));
            subMap.put("LCM2内损", subMap.get("LCM2内损") + map.get("LCM2内损"));
            subMap.put("LCM外损", subMap.get("LCM外损") + map.get("LCM外损"));
            subMap.put("CELL外损", subMap.get("CELL外损") + map.get("CELL外损"));
            subMap.put("新产品费用", subMap.get("新产品费用") + map.get("新产品费用"));

        }

        //营业额
        //List<Turnover> turnoverList = turnoverRepository.findBySeasonGreaterThanEqualAndSeasonLessThanEqual(seasonList.get(0), seasonList.get(seasonList.size()-1));
        List<Turnover> turnoverList = turnoverRepository.findAll();
        for(Turnover turnover : turnoverList) {
            String season = turnover.getSeason();
            Map<String, Double> subMap = map_season.get(season);
            if(subMap==null) continue;
            subMap.put("营业额", turnover.getAmount());
        }

        List<Map> mapList = new ArrayList<>();
        for(String season : seasonList) {
            Map map = new HashMap();
            map.put("season", season);
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

    public List<String> monthPre(String fromMonth, String toMonth) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Date fromDate = sdf.parse(fromMonth);
        Date toDate = sdf.parse(toMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(calendar.MONTH, -5); //得到前9月的时间
        Date dBefore = calendar.getTime();

        List<String> monthList = new ArrayList<>();
        Calendar min = Calendar.getInstance();
        min.setTime(dBefore);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        Calendar max = Calendar.getInstance();
        max.setTime(toDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            monthList.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return monthList;
    }
}
