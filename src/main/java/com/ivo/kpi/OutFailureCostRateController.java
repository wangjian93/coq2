package com.ivo.kpi;

import com.ivo.common.result.PageResult;
import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.report.repository.WbRatioRepository;
import com.ivo.coq.report.service.WbRatioService;
import com.ivo.product.entity.ObaMonth;
import com.ivo.product.repository.LocationCostRepository;
import com.ivo.product.repository.ObaMonthRepository;
import com.ivo.product.repository.ObaRepository;
import com.ivo.product.repository.ScrapDetailRepository;
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
@RequestMapping("/outFailureCostRate")
public class OutFailureCostRateController {

    @Resource
    private WbRatioService wbRatioService;

    @Resource
    private LocationCostRepository locationCostRepository;

    @Resource
    private ScrapDetailRepository scrapDetailRepository;

    @Resource
    private ObaRepository obaRepository;

    @Resource
    private ObaMonthRepository obaMonthRepository;

    @Resource
    private WbRatioRepository wbRatioRepository;

    @RequestMapping("/getDetail")
    public Result getDetail(String FAB_ID, String fromMonth, String toMonth) throws ParseException {
        fromMonth = fromMonth.replace("-", "");
        toMonth = toMonth.replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        List<String> monthList = monthPre(fromMonth, toMonth);

        Map<String, Map> month_map = new HashMap<>();
        for(String month : monthList) {
            Map<String, Double> subMap = new HashMap<>();
            subMap.put("b_trip", 0d);
            subMap.put("scrap", 0d);
            subMap.put("oba", 0d);
            subMap.put("product", 0d);
            month_map.put(month, subMap);
        }


        if(FAB_ID.equals("WB_CELL"))  FAB_ID = "WB_CEL";
        List<Map> list_btrip = locationCostRepository.getB_TripAmuntMonth(FAB_ID, sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1)));
        List<Map> list_scrap = locationCostRepository.getScrapAmountMonth(FAB_ID, sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1)));
        for(Map map : list_btrip) {
            String month = (String) map.get("month");
            double amount = (double) map.get("amount");
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("b_trip", amount);
        }
        for(Map map : list_scrap) {
            String month = (String) map.get("month");
            double amount = (double) map.get("amount");
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("scrap", amount);
        }

        String fab = FAB_ID;
        if(fab.equals("WB_CEL")) fab = "CELL";
        if(fab.equals("WB_LCM")) fab = "LCM";
        List<ObaMonth> obaMonthList = obaMonthRepository.findByFabAndMonthIn(fab, monthList);
        for(ObaMonth obaMonth : obaMonthList) {
            String month = obaMonth.getMonth();
            double amount = obaMonth.getAmount();
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("oba", amount);
        }

        List<Map> list_total = wbRatioRepository.getTotalProductAmount(FAB_ID, monthList);
        for(Map map : list_total) {
            String month = (String) map.get("month");
            double amount = (double) map.get("amount");
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("product", amount);
        }

        List<Map> mapList = new ArrayList<>();
        double total_trip = 0;
        double total_scrap = 0;
        double total_oba = 0;
        double total_product = 0;
        for(int i=0; i<monthList.size(); i++) {
            String month = monthList.get(i);
            Map map = new HashMap();
            map.put("month", month);
            map.putAll(month_map.get(month));
            total_trip += (double)map.get("b_trip");
            total_scrap += (double)map.get("scrap");
            total_oba += (double)map.get("oba");
            if(i==2 || i==5) {
                total_product += (double)map.get("product");
            } else {
                map.put("product", 0);
            }
            mapList.add(map);
        }
        Map total_map = new HashMap();
        total_map.put("month", "TOTAL");
        total_map.put("b_trip", total_trip);
        total_map.put("scrap", total_scrap);
        total_map.put("oba", total_oba);
        total_map.put("product", total_product);
        total_map.put("rate", (total_trip+total_scrap+total_oba)/total_product*100);
        mapList.add(total_map);
        return ResultUtil.successPage(mapList);
    }


    @RequestMapping("/getRate")
    public Result getOutFailureCostRate(String FAB_ID, String fromMonth, String toMonth) throws ParseException {
        if(FAB_ID.equals("CELL")) FAB_ID = "WB_CEL";
        if(FAB_ID.equals("LCM")) FAB_ID = "WB_LCM";
        fromMonth = fromMonth.replace("-", "");
        toMonth = toMonth.replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        List<String> monthList = monthPre(fromMonth, toMonth);

        Map<String, Map> month_map = new HashMap<>();
        for(String month : monthList) {
            Map<String, Double> subMap = new HashMap<>();
            subMap.put("b_trip", 0d);
            subMap.put("scrap", 0d);
            subMap.put("oba", 0d);
            subMap.put("product", 0d);
            month_map.put(month, subMap);
        }

        List<Map> list_btrip = locationCostRepository.getB_TripAmuntMonth(FAB_ID, sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1)));
        List<Map> list_scrap = locationCostRepository.getScrapAmountMonth(FAB_ID, sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1)));
        for(Map map : list_btrip) {
            String month = (String) map.get("month");
            double amount = (double) map.get("amount");
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("b_trip", amount);
        }
        for(Map map : list_scrap) {
            String month = (String) map.get("month");
            double amount = (double) map.get("amount");
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("scrap", amount);
        }

        String fab = FAB_ID;
        if(fab.equals("WB_CEL")) fab = "CELL";
        if(fab.equals("WB_LCM")) fab = "LCM";
        List<ObaMonth> obaMonthList = obaMonthRepository.findByFabAndMonthIn(fab, monthList);
        for(ObaMonth obaMonth : obaMonthList) {
            String month = obaMonth.getMonth();
            double amount = obaMonth.getAmount();
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("oba", amount);
        }

        List<Map> list_total = wbRatioRepository.getTotalProductAmount(FAB_ID, monthList);
        for(Map map : list_total) {
            String month = (String) map.get("month");
            double amount = (double) map.get("amount");
            Map subMap = month_map.get(month);
            if(subMap == null) continue;
            subMap.put("product", amount);
        }


        List<Map> list = new ArrayList<>();
        for(int i=0; i<monthList.size(); i++) {
            if(i<5) continue;
            String month = monthList.get(i);
            double total_trip = 0;
            double total_scrap = 0;
            double total_oba = 0;
            double total_product = 0;
            for(int j=0; j<6; j++) {
                Map subMap = month_map.get(monthList.get(i-j));
                total_trip += (double) subMap.get("b_trip");
                total_scrap += (double) subMap.get("scrap");
                total_oba += (double) subMap.get("oba");
                if(j==0 || j == 3) {
                    total_product += (double) subMap.get("product");
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("month", month);
            map.put("price", total_trip+total_scrap);
            map.put("obaAmount", total_oba);
            map.put("amount", total_product);
            map.put("ratio", (total_trip+total_scrap+total_oba)/total_product);
            map.put("fab", FAB_ID);
            list.add(map);
        }

        return ResultUtil.successPage(list);
    }



    @RequestMapping("/getB_tripAmountDetail")
    public Result getB_tripDetail(String FAB_ID, String fromMonth, String toMonth) throws ParseException {
        if(FAB_ID.equals("WB_CELL"))  FAB_ID = "WB_CEL";
        fromMonth = fromMonth.replace("-", "");
        toMonth = toMonth.replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        List<String> monthList = monthPre(fromMonth, toMonth);
        List<Map> mapList = locationCostRepository.getB_TripAmuntMonthDetail(FAB_ID, sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1)));
        return ResultUtil.successPage(mapList);
    }

    @RequestMapping("/getScrapAmountDetail")
    public Result getScrapAmountDetail(String FAB_ID, String fromMonth, String toMonth) throws ParseException {
        if(FAB_ID.equals("WB_CELL"))  FAB_ID = "WB_CEL";
        fromMonth = fromMonth.replace("-", "");
        toMonth = toMonth.replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        List<String> monthList = monthPre(fromMonth, toMonth);
        List<Map> mapList = locationCostRepository.getScrapAmountMonthDetail(FAB_ID, sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1)));
        return ResultUtil.successPage(mapList);
    }

    @RequestMapping("/getScrapDetail")
    public PageResult getScrapDetail(String FAB_ID, String fromMonth, String toMonth) throws ParseException {
        if(FAB_ID.equals("WB_CELL"))  FAB_ID = "WB_CEL";
        fromMonth = fromMonth.replace("-", "");
        toMonth = toMonth.replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        List<String> monthList = monthPre(fromMonth, toMonth);

        return ResultUtil.successPage(scrapDetailRepository.findByFabAndFabDateBetween(FAB_ID, sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1))));
    }

    public List<String> monthPre(String fromMonth, String toMonth) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Date fromDate = sdf.parse(fromMonth);
        Date toDate = sdf.parse(toMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(calendar.MONTH, -5); //得到前5月的时间
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


    @RequestMapping("/getOba")
    public PageResult getOba(String FAB_ID, String fromMonth, String toMonth) throws ParseException {
        String fab = FAB_ID;
        if(fab.equals("WB_CELL")) fab = "CELL";
        if(fab.equals("WB_LCM")) fab = "LCM";

        fromMonth = fromMonth.replace("-", "");
        toMonth = toMonth.replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        List<String> monthList = monthPre(fromMonth, toMonth);
        return ResultUtil.successPage(obaRepository.getObaAmont(sdf.parse(monthList.get(0)), sdf.parse(monthList.get(monthList.size()-1)), fab));
    }

}
