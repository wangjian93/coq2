//package com.ivo.coq.station.service.impl;
//
//import com.ivo.common.utils.DoubleUtil;
//import com.ivo.coq.station.entity.EqStation;
//import com.ivo.coq.station.entity.StationCostCell;
//import com.ivo.coq.station.entity.WorkRate;
//import com.ivo.coq.station.repository.EqStationRepository;
//import com.ivo.coq.station.repository.StationCostCellRepository;
//import com.ivo.coq.station.repository.WorkRateRepository;
//import com.ivo.coq.station.service.StationCostCellService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author wj
// * @version 1.0
// */
////@Service
//public class StationCostCellServiceImpl implements StationCostCellService {
//
//    @Autowired
//    private StationCostCellRepository stationCostCellRepository;
//
//    @Autowired
//    private EqStationRepository eqStationRepository;
//
//    @Autowired
//    private WorkRateRepository workRateRepository;
//
//
//    @Override
//    public void computeStationCostCell(String project) {
//        String month = "2021-03";
//        List<StationCostCell> stationCostCellList = stationCostCellRepository.findByMonthAndProject(month, project);
//        double totalRework = 0;
//
//        //判断按大板计算还是小板计算
//        List<String> stationList = new ArrayList<>();
//        for(StationCostCell stationCostCell : stationCostCellList) {
//            stationList.add(stationCostCell.getStation());
//        }
//        //如果投入站点为0000，未通过0500站点，则按大板计算
//        //如果投入站点为0000，通过0500站点，则按小板计算
//        //如果投入站点非0000，通过0500站点，则按小板计算
//        boolean isBigOrSmall = false;
//        if(stationList.contains("0000") && !stationList.contains("0500")) {
//            isBigOrSmall = true;
//        }
//
//        for(StationCostCell stationCostCell : stationCostCellList) {
//
//            //生产费用=主站点对应的主站点CT（不含等待）工时*单位费率+检查站点对应的检查站点CT（不含等待）工时*单位费率
//            //重工费用=Rework站点对应的Rework站点CT（不含等待）工时*单位费率
//            //报废费用=通过的主站点、检查站点和Rework站点的单片工时*各对应的单位费率
//
//            String eq = stationCostCell.getEq();
//            EqStation eqStation = eqStationRepository.findFirstByFabAndEq("CELL", eq);
//            if(eqStation == null) {
//                stationCostCell.setMemo("机台对应工作中心没有");
//                stationCostCellRepository.save(stationCostCell);
//                continue;
//            }
//            String workCenter = eqStation.getWorkCenter();
//            List<WorkRate> workRateList = workRateRepository.findByMonthAndCostCenter(month, workCenter);
//            if(workRateList == null || workRateList.size()==0) {
//                stationCostCell.setMemo("工作中心的费率没有");
//                stationCostCellRepository.save(stationCostCell);
//                continue;
//            }
//            double price = 0;
//            for(WorkRate workRate : workRateList) {
//                price = DoubleUtil.sum(price, workRate.getPrice());
//            }
//
//            double mainWorkHours = stationCostCell.getMainWorkHours();
//            double reworkHours = stationCostCell.getReworkHours();
//            double checkWorkHours = stationCostCell.getCheckWorkHours();
//
//            //大板除以切片数
//            //获取切片数
//            double cut;
//            if(project.equals("N1568V")) {
//                cut = 18;
//            } else if(project.equals("A0906")){
//                cut = 50;
//            } else {
//                cut = 1;
//            }
//
//            double perProductCost;
//            if(isBigOrSmall && (stationCostCell.getMainQty()>0 || stationCostCell.getCheckQty()>0)) {
//                perProductCost = (mainWorkHours*24*price*60/cut) + (checkWorkHours*24*price*60/cut);
//                stationCostCell.setMemo("按大板计算");
//            } else {
//                perProductCost = (mainWorkHours*24*price*60) + (checkWorkHours*24*price*60);
//                stationCostCell.setMemo("按小板计算");
//            }
//
//            double perReworkCost;
//            if(isBigOrSmall && stationCostCell.getReworkQty()>0) {
//                perReworkCost = reworkHours*24*price*60/cut;
//                stationCostCell.setMemo("按大板计算");
//            } else {
//                perReworkCost = reworkHours*24*price*60;
//                stationCostCell.setMemo("按小板计算");
//            }
//            totalRework = DoubleUtil.sum(totalRework, perReworkCost);
//            double perReworkCost_ = totalRework;
//
//            stationCostCell.setCut(cut);
//            stationCostCell.setWorkRate(price);
//            stationCostCell.setPerProductCost(perProductCost);
//            stationCostCell.setPerReworkCost(perReworkCost);
//            stationCostCell.setPerReworkCost_(perReworkCost_);
//            stationCostCellRepository.save(stationCostCell);
//        }
//    }
//}
