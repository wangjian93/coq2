package com.ivo.kpi;

import com.ivo.common.result.PageResult;
import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.report.repository.InLossAmountRepository;
import com.ivo.product.repository.*;
import com.ivo.product.service.MardService;
import com.ivo.product.service.WoCloseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 内部失败成本率
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/inFailureCostRate")
public class InFailureCostRateController {


    @Resource
    private InLossAmountRepository inLossAmountRepository;

    @Resource
    private ReworkScrapAryRepository reworkScrapAryRepository;

    @Resource
    private ReworkScrapCellRepository reworkScrapCellRepository;

    @Resource
    private ReworkScrapLcm1Repository reworkScrapLcm1Repository;

    @Resource
    private ReworkScrapLcm2Repository reworkScrapLcm2Repository;

    @Resource
    private WhScrapRepository whScrapRepository;

    @Resource
    private WoCloseService woCloseService;

    @Resource
    private MardService mardService;

    @Resource
    private ObaRepository obaRepository;

    @Resource
    private LocationCostRepository locationCostRepository;

    @Resource
    private ScrapDetailRepository scrapDetailRepository;


    @RequestMapping("/getRate")
    public Result getInFailureCostRateAry(String fromMonth, String toMonth, String fab) {
        fromMonth = fromMonth.replace("-", "");
        toMonth = toMonth.replace("-", "");

        String[] fabs;
        if(StringUtils.isEmpty(fab)) {
            fabs = new String[] {"CELL","ARRAY","LCM1","LCM2"};
        } else {
            fabs = fab.split(",");
        }
        List<Map> mapList = inLossAmountRepository.getRate(fromMonth, toMonth, fabs);
        return ResultUtil.success(mapList);
    }


    @RequestMapping("/getReworkScrapData")
    public PageResult getReworkScrapData(String month, String fab) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date fromDate;
        Date toDate;
        fromDate = sdf.parse(month);
        Calendar ca = Calendar.getInstance();
        ca.setTime(fromDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        toDate = ca.getTime();

        List list;
        if(fab.equals("ARRAY")) {
            list = reworkScrapAryRepository.getMpReworkScrap(fromDate, toDate);
        } else if(fab.equals("CELL")) {
            list = reworkScrapCellRepository.findByFabDateBetween(fromDate, toDate);
        } else if(fab.equals("LCM1")) {
            list = reworkScrapLcm1Repository.getScrap(fromDate, toDate);
        } else {
            list = reworkScrapLcm2Repository.getScrap(fromDate, toDate);
        }
        return ResultUtil.successPage(list);
    }

    @RequestMapping("/getWhScrapData")
    public PageResult getWhScrapData(String month, String fab) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date fromDate;
        Date toDate;
        fromDate = sdf.parse(month);

        Calendar ca = Calendar.getInstance();
        ca.setTime(fromDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        toDate = ca.getTime();

        String[] userDepartments = {"10000684","10000578"};   //外包服务部
        String costCenter_LCM1="M0002";
        String costCenter_LCM2="B3002";
        String costCenter_CELL="B2200";
        String costCenter_ARRAY="B2100";

        String costCenter;
        if(fab.equals("ARRAY")) {
            costCenter = costCenter_ARRAY;
        } else if(fab.equals("CELL")) {
            costCenter = costCenter_CELL;
        } else if(fab.equals("LCM1")) {
            costCenter = costCenter_LCM1;
        } else {
            costCenter = costCenter_LCM2;
        }

        List list = whScrapRepository.getWhScrapByCostCenterAndUserDepartment(costCenter, userDepartments, fromDate, toDate);
        return ResultUtil.successPage(list);
    }

    @RequestMapping("/getWoCloseData")
    public PageResult getWoCloseData(String month, String fab) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date fromDate;
        Date toDate;
        fromDate = sdf.parse(month);

        Calendar ca = Calendar.getInstance();
        ca.setTime(fromDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        toDate = ca.getTime();

        List list;
        if(fab.equals("LCM1")) {
            list = woCloseService.getWoCloseLcm1(fromDate, toDate);
        } else {
            list = woCloseService.getWoCloseLcm2(fromDate, toDate);
        }
        return ResultUtil.successPage(list);
    }

    @RequestMapping("/getMardData")
    public PageResult getMardData(String month, String fab) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date fromDate;
        Date toDate;
        fromDate = sdf.parse(month);

        Calendar ca = Calendar.getInstance();
        ca.setTime(fromDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        toDate = ca.getTime();

        List list;
        if(fab.equals("LCM1")) {
            list = mardService.getMardLcm1(fromDate, toDate);
        } else {
            list = mardService.getMardLcm2(fromDate, toDate);
        }
        return ResultUtil.successPage(list);
    }

    @RequestMapping("/getOba")
    public PageResult getOba(String month, String fab) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date fromDate;
        Date toDate;
        fromDate = sdf.parse(month);

        Calendar ca = Calendar.getInstance();
        ca.setTime(fromDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        toDate = ca.getTime();
        return ResultUtil.successPage(obaRepository.getObaAmont(fromDate, toDate, fab));
    }
}
