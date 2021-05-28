//package com.ivo.coq.station.service.impl;
//
//import com.ivo.common.utils.DoubleUtil;
//import com.ivo.coq.station.entity.EqStation;
//import com.ivo.coq.station.entity.StationCostAry;
//import com.ivo.coq.station.entity.WorkRate;
//import com.ivo.coq.station.repository.EqStationRepository;
//import com.ivo.coq.station.repository.StationCostAryRepository;
//import com.ivo.coq.station.repository.WorkRateRepository;
//import com.ivo.coq.station.service.StationCycleTimeAryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * @author wj
// * @version 1.0
// */
////@Service
//public class StationCostAryServiceImpl implements StationCycleTimeAryService {
//
//    @Autowired
//    private StationCostAryRepository stationCostAryRepository;
//
//    @Autowired
//    private EqStationRepository eqStationRepository;
//
//    @Autowired
//    private WorkRateRepository workRateRepository;
//
//    @Override
//    public void computeStationCostAry(String project) {
//
//        String month = "2021-03";
//        List<StationCostAry> stationCostAryList = stationCostAryRepository.findByMonthAndProject(month, project);
//        double totalRework = 0;
//        for(StationCostAry stationCostAry : stationCostAryList) {
//
//            //生产费用=S_CNT数量对应的S财务成本工时*单位费率+M_CNT数量对应的M财务成本工时*单位费率
//            //重工费用=R_CNT数量对应的R财务成本工时*单位费率+B_CNT数量对应的B财务成本工时*单位费率
//            //报废费用=通过的S、M站点的单片工时*各对应的单位费率
//
//            String eq = stationCostAry.getEq();
//            EqStation eqStation = eqStationRepository.findFirstByFabAndEq("ARRAY", eq);
//            if(eqStation == null) {
//                stationCostAry.setMemo("机台对应工作中心没有");
//                stationCostAryRepository.save(stationCostAry);
//                continue;
//            }
//            String workCenter = eqStation.getWorkCenter();
//            List<WorkRate> workRateList = workRateRepository.findByMonthAndCostCenter(month, workCenter);
//            if(workRateList == null || workRateList.size()==0) {
//                stationCostAry.setMemo("工作中心的费率没有");
//                stationCostAryRepository.save(stationCostAry);
//                continue;
//            }
//            double price = 0;
//            for(WorkRate workRate : workRateList) {
//                price = DoubleUtil.sum(price, workRate.getPrice());
//            }
//
//            double b_cnt = stationCostAry.getB_cnt();
//            double b_workHours = stationCostAry.getB_workHours();
//            double s_cnt = stationCostAry.getS_cnt();
//            double s_workHours = stationCostAry.getS_workHours();
//            double r_cnt = stationCostAry.getR_cnt();
//            double r_workHours = stationCostAry.getR_workHours();
//            double m_cnt = stationCostAry.getM_cnt();
//            double m_workHours = stationCostAry.getM_workHours();
//
//
//            double perProductCost = (s_workHours*price*60) + (m_workHours*price*60);
//            double perReworkCost = (r_workHours*price*60) + (b_workHours*price*60);
//            totalRework = DoubleUtil.sum(totalRework, perReworkCost);
//            double perReworkCost_ = totalRework;
//
//            stationCostAry.setWorkRate(price);
//            stationCostAry.setPerProductCost(perProductCost);
//            stationCostAry.setPerReworkCost(perReworkCost);
//            stationCostAry.setPerReworkCost_(perReworkCost_);
//            stationCostAryRepository.save(stationCostAry);
//        }
//    }
//}
