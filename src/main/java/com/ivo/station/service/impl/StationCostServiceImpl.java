//package com.ivo.station.service.impl;
//
//import com.ivo.station.entity.StationCostAry;
//import com.ivo.station.entity.StationCostCell;
//import com.ivo.station.entity.StationCostLcm;
//import com.ivo.station.repository.StationCostAryRepository;
//import com.ivo.station.repository.StationCostCellRepository;
//import com.ivo.station.repository.StationCostLcmRepository;
//import com.ivo.station.service.StationCostService;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author wj
// * @version 1.0
// */
//@Service
//public class StationCostServiceImpl implements StationCostService {
//
//    @Resource
//    private StationCostAryRepository stationCostAryRepository;
//
//    @Resource
//    private StationCostCellRepository stationCostCellRepository;
//
//    @Resource
//    private StationCostLcmRepository stationCostLcmRepository;
//
//    @Override
//    public double getPerProductAmountAry(String project, String month) {
//        //project机种不带版本('N1568V R0')
//        if(StringUtils.contains(project, " ")) {
//            project = project.substring(0, project.indexOf(" "));
//        }
//
//        //如果不指定月份，取最近的月份
//        if(StringUtils.isEmpty(month)) {
//            month = stationCostAryRepository.getLastMonth(project);
//        }
//        List<StationCostAry> stationList = stationCostAryRepository.findByProjectAndMonthOrderByStation(project, month);
//
//        double amount = 0;
//        for(StationCostAry station : stationList) {
//            amount += station.getAmount();
//        }
//
//        return amount;
//    }
//
//    @Override
//    public double getPerProductAmountCell(String project, String month) {
//        //project机种不带版本('N1568V R0')
//        if(StringUtils.contains(project, " ")) {
//            project = project.substring(0, project.indexOf(" "));
//        }
//
//        //如果不指定月份，取最近的月份
//        if(StringUtils.isEmpty(month)) {
//            month = stationCostCellRepository.getLastMonth(project);
//        }
//        List<StationCostCell> stationList = stationCostCellRepository.findByProjectAndMonthOrderByStation(project, month);
//
//        double amount = 0;
//        for(StationCostCell station : stationList) {
//            amount += station.getAmount();
//        }
//
//        return amount;
//    }
//
//    @Override
//    public double getPerProductAmountLcm(String product, String month) {
//
//        //如果不指定月份，取最近的月份
//        if(StringUtils.isEmpty(month)) {
//            month = stationCostLcmRepository.getLastMonth(product);
//        }
//        List<StationCostLcm> stationList = stationCostLcmRepository.findByProductAndMonthOrderByStation(product, month);
//
//        double amount = 0;
//        for(StationCostLcm station : stationList) {
//            amount += station.getAmount();
//        }
//
//        return amount;
//    }
//
//    @Override
//    public double getPerScrapAmountAry(String project, String month, String station) {
//        //project机种不带版本('N1568V R0')
//        if(StringUtils.contains(project, " ")) {
//            project = project.substring(0, project.indexOf(" "));
//        }
//
//        //如果不指定月份，取最近的月份
//        if(StringUtils.isEmpty(month)) {
//            month = stationCostAryRepository.getLastMonth(project);
//        }
//
//        StationCostAry stationCostAry = stationCostAryRepository.findFirstByProjectAndMonthAndStationLike(project, month, station+"%");
//        return stationCostAry==null ? 0 : stationCostAry.getAmount();
//    }
//
//    @Override
//    public double getPerScrapAmountCell(String project, String month, String station) {
//        //project机种不带版本('N1568V R0')
//        if(StringUtils.contains(project, " ")) {
//            project = project.substring(0, project.indexOf(" "));
//        }
//
//        //如果不指定月份，取最近的月份
//        if(StringUtils.isEmpty(month)) {
//            month = stationCostCellRepository.getLastMonth(project);
//        }
//        StationCostCell stationCostCell = stationCostCellRepository.findFirstByProjectAndMonthAndStationLike(project, month, station+"%");
//        return stationCostCell==null ? 0 : stationCostCell.getAmount();
//    }
//
//    @Override
//    public double getPerScrapAmountLcm( String product, String month, String station) {
//
//        //如果不指定月份，取最近的月份
//        if(StringUtils.isEmpty(month)) {
//            month = stationCostLcmRepository.getLastMonth(product);
//        }
//
//        StationCostLcm stationCostLcm = stationCostLcmRepository.findFirstByProductAndMonthAndStationLike(product, month, station+"%");
//        return stationCostLcm==null ? 0 : stationCostLcm.getAmount();
//    }
//
//    @Override
//    public double getPerReworkAmountAry(String project, String month, String station) {
//        //project机种不带版本('N1568V R0')
//        if(StringUtils.contains(project, " ")) {
//            project = project.substring(0, project.indexOf(" "));
//        }
//
//        //如果不指定月份，取最近的月份
//        if(StringUtils.isEmpty(month)) {
//            month = stationCostAryRepository.getLastMonth(project);
//        }
//        List<StationCostAry> stationCostAryList = stationCostAryRepository.findByProjectAndMonthOrderByStation(project, month);
//
//        //重工费用：前面经过的所有站点的重工费用之和
//        double amount = 0;
//        for(StationCostAry stationCostAry : stationCostAryList) {
//            amount += stationCostAry.getReworkAmount();
//            if(StringUtils.equals(station, stationCostAry.getStation())) break;
//        }
//        return amount;
//    }
//
//    @Override
//    public double getPerReworkAmountCell(String project, String month, String station) {
//        //project机种不带版本('N1568V R0')
//        if(StringUtils.contains(project, " ")) {
//            project = project.substring(0, project.indexOf(" "));
//        }
//
//        //如果不指定月份，取最近的月份
//        if(StringUtils.isEmpty(month)) {
//            month = stationCostCellRepository.getLastMonth(project);
//        }
//        List<StationCostCell> stationCostCellList = stationCostCellRepository.findByProjectAndMonthOrderByStation(project, month);
//
//        //重工费用：前面经过的所有站点的重工费用之和
//        double amount = 0;
//        for(StationCostCell stationCostCell : stationCostCellList) {
//            amount += stationCostCell.getReworkAmount();
//            if(StringUtils.equals(station, stationCostCell.getStation())) break;
//        }
//        return amount;
//    }
//
//    @Override
//    public double getPerReworkAmountLcm(String product, String month, String station) {
//        //如果不指定月份，取最近的月份
//        if(StringUtils.isEmpty(month)) {
//            month = stationCostLcmRepository.getLastMonth(product);
//        }
//        List<StationCostLcm> stationCostLcmList = stationCostLcmRepository.findByProductAndMonthOrderByStation(product, month);
//
//        //重工费用：前面经过的所有站点的重工费用之和
//        double amount = 0;
//        for(StationCostLcm stationCostLcm : stationCostLcmList) {
//            amount += stationCostLcm.getReworkAmount();
//            if(StringUtils.equals(station, stationCostLcm.getStation())) break;
//        }
//        return amount;
//    }
//}
