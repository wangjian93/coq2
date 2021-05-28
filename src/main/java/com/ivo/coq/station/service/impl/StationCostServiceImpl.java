//package com.ivo.coq.station.service.impl;
//
//import com.ivo.common.enums.PlantEnum;
//import com.ivo.coq.project.repository.EngineeringExperimentWoRepository;
//import com.ivo.coq.station.entity.StationCostAry;
//import com.ivo.coq.station.entity.StationCostCell;
//import com.ivo.coq.station.entity.StationCostLcm;
//import com.ivo.coq.station.repository.StationCostAryRepository;
//import com.ivo.coq.station.repository.StationCostCellRepository;
//import com.ivo.coq.station.repository.StationCostLcmRepository;
//import com.ivo.coq.station.service.StationCostService;
//import com.ivo.rest.oracle.OracleService;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//
///**
// * @author wj
// * @version 1.0
// */
////@Service
//public class StationCostServiceImpl implements StationCostService {
//
//    @Autowired
//    private StationCostAryRepository stationCostAryRepository;
//
//    @Autowired
//    private StationCostCellRepository stationCostCellRepository;
//
//    @Autowired
//    private EngineeringExperimentWoRepository engineeringExperimentWoRepository;
//
//
//    @Autowired
//    private OracleService oracleService;
//
//    @Autowired
//    private StationCostLcmRepository stationCostLcmRepository;
//
//    @Override
//    public double getPerProductAmountAry(String project) {
//        String month = "2021-03";
//        double total = 0;
//        List<StationCostAry> stationCostAryList = stationCostAryRepository.findByMonthAndProject(month, project);
//        for(StationCostAry stationCostAry : stationCostAryList) {
//            total += stationCostAry.getPerProductCost();
//        }
//        return total;
//    }
//
//    @Override
//    public double getPerProductAmountCell(String project) {
//        String month = "2021-03";
//        double total = 0;
//        List<StationCostCell> stationCostCellList = stationCostCellRepository.findByMonthAndProject(month, project);
//        for(StationCostCell stationCostCell : stationCostCellList) {
//            total += stationCostCell.getPerProductCost();
//        }
//        return total;
//    }
//
//    @Override
//    public double getPerProductAmountLcm(String project, String ee) {
//        if(ee == null || !StringUtils.startsWithIgnoreCase(ee, "EE") || !project.equals("N1568V") || !project.equals("A0906")) return 0;
//        project = project+ " R0";
//        List<String> woList = engineeringExperimentWoRepository.getWoByEe(project, ee);
//        HashMap<String, Double> woAmountMap = new HashMap<>();
//        if(project.equals("N1568V R0")) {
//            woAmountMap.put("IL2M201107", 272.4);
//            woAmountMap.put("IL2M201108", 272.4);
//            woAmountMap.put("IL2M203111", 272.4);
//            woAmountMap.put("IL2M203112", 272.4);
//            woAmountMap.put("IL2M203124", 272.4);
//            woAmountMap.put("IL2M206111", 272.4);
//            woAmountMap.put("IO2M19C107", 37.12);
//            woAmountMap.put("IO2M19C108", 37.12);
//            woAmountMap.put("IO2M19C114", 71.46);
//            woAmountMap.put("IO2M19C115", 71.46);
//            woAmountMap.put("IO2M202101", 71.46);
//            woAmountMap.put("IO2M202102", 37.12);
//            woAmountMap.put("IO2M206102", 37.12);
//            woAmountMap.put("IO2M206103", 37.12);
//            woAmountMap.put("IO2M206104", 37.12);
//            woAmountMap.put("IO2M206105", null);
//            woAmountMap.put("IO2M206106", 71.46);
//        } else {
//            woAmountMap.put("IL2M203120", 125.53);
//            woAmountMap.put("IL2M203121", 125.53);
//            woAmountMap.put("IL2M203122", 125.53);
//            woAmountMap.put("IL2M203123", 133.99);
//            woAmountMap.put("IL2M205114", 125.53);
//            woAmountMap.put("IL2M205115", 133.99);
//        }
//
//
//        double total = 0;
//        for(String wo : woList) {
//            double amount = woAmountMap.get(wo)==null? 0 : woAmountMap.get(wo);
//            total += amount;
//        }
//        return total;
//    }
//
//    @Override
//    public double getProductAmountLcm(String project, String ee) {
//        if(ee == null || !StringUtils.startsWithIgnoreCase(ee, "EE") || !project.equals("N1568V") || !project.equals("A0906")) return 0;
//        project = project+ " R0";
//        List<String> woList = engineeringExperimentWoRepository.getWoByEe(project, ee);
//        HashMap<String, Double> woAmountMap = new HashMap<>();
//        if(project.equals("N1568V R0")) {
//            woAmountMap.put("IL2M201107", 272.4);
//            woAmountMap.put("IL2M201108", 272.4);
//            woAmountMap.put("IL2M203111", 272.4);
//            woAmountMap.put("IL2M203112", 272.4);
//            woAmountMap.put("IL2M203124", 272.4);
//            woAmountMap.put("IL2M206111", 272.4);
//            woAmountMap.put("IO2M19C107", 37.12);
//            woAmountMap.put("IO2M19C108", 37.12);
//            woAmountMap.put("IO2M19C114", 71.46);
//            woAmountMap.put("IO2M19C115", 71.46);
//            woAmountMap.put("IO2M202101", 71.46);
//            woAmountMap.put("IO2M202102", 37.12);
//            woAmountMap.put("IO2M206102", 37.12);
//            woAmountMap.put("IO2M206103", 37.12);
//            woAmountMap.put("IO2M206104", 37.12);
//            woAmountMap.put("IO2M206105", null);
//            woAmountMap.put("IO2M206106", 71.46);
//        } else {
//            woAmountMap.put("IL2M203120", 125.53);
//            woAmountMap.put("IL2M203121", 125.53);
//            woAmountMap.put("IL2M203122", 125.53);
//            woAmountMap.put("IL2M203123", 133.99);
//            woAmountMap.put("IL2M205114", 125.53);
//            woAmountMap.put("IL2M205115", 133.99);
//        }
//
//        double total = 0;
//        for(String wo : woList) {
//            Double qty = oracleService.getWoShippingQty(wo);
//            Double price = woAmountMap.get(wo);
//            if (qty == null) qty = 0D;
//            if(price == null) price = 0D;
//
//            total += qty*price;
//        }
//        return total;
//    }
//
//    @Override
//    public double getPerReworkAmountAry(String project, String station) {
//        String month = "2021-03";
//        StationCostAry stationCostAry = stationCostAryRepository.findFirstByMonthAndProjectAndStationLike(month, project, station);
//        if(stationCostAry == null) return 0;
//        return stationCostAry.getPerReworkCost_();
//    }
//
//    @Override
//    public double getPerReworkAmountCell(String project, String station) {
//        String month = "2021-03";
//        StationCostCell stationCostCell = stationCostCellRepository.findFirstByMonthAndProjectAndStationLike(month, project, station);
//        if(stationCostCell == null) return 0;
//        return stationCostCell.getPerReworkCost_();
//    }
//
//    @Override
//    public double getPerScrapAmountAry(String project, String station) {
//        String month = "2021-03";
//        StationCostAry stationCostAry = stationCostAryRepository.findFirstByMonthAndProjectAndStationLike(month, project, station);
//        if(stationCostAry == null) return 0;
//        return stationCostAry.getPerProductCost();
//    }
//
//    @Override
//    public double getPerScrapAmountCell(String project, String station) {
//        String month = "2021-03";
//        StationCostCell stationCostCell = stationCostCellRepository.findFirstByMonthAndProjectAndStationLike(month, project, station);
//        if(stationCostCell == null) return 0;
//        return stationCostCell.getPerProductCost();
//    }
//
//    @Override
//    public double getPerScrapAmountLcm(String project, String station, String product) {
//        String month = "2021-03";
//        StationCostLcm stationCostLcm = stationCostLcmRepository.findFirstByMonthAndProjectAndProductAndStationLike(month, project, product, station);
//        if(stationCostLcm == null) return 0;
//        return stationCostLcm.getPerProductCost();
//    }
//
//
//    @Override
//    public String matchStation(String project, String factory, String station) {
//        String month = "2021-03";
//        if(StringUtils.isEmpty(station)) return null;
//        // CELL站点只有三码时首部加0
//        if(StringUtils.equalsIgnoreCase(factory, "Cell") && station.length()==3) {
//            station = "0" + station;
//        }
//
//        // 匹配不到站点时，匹配前三码，还没有然后再前两码
//        if(PlantEnum.Array.getPlant().equals(factory)) {
//            StationCostAry stationCostAry = stationCostAryRepository.findFirstByMonthAndProjectAndStationLike(month, project, station+"%");
//            if(stationCostAry == null) {
//                if(station.length()>3) {
//                    station = station.substring(0, 3);
//                }
//                stationCostAry = stationCostAryRepository.findFirstByMonthAndProjectAndStationLike(month, project, station+"%");
//                if(stationCostAry == null) {
//                    if(station.length()>2) {
//                        station = station.substring(0, 2);
//                    }
//                    stationCostAry = stationCostAryRepository.findFirstByMonthAndProjectAndStationLike(month, project, station+"%");
//                }
//            }
//            if(stationCostAry == null) {
//                return null;
//            } else {
//                return stationCostAry.getStation();
//            }
//        } else if(PlantEnum.Cell.getPlant().equals(factory)) {
//            StationCostCell stationCostCell = stationCostCellRepository.findFirstByMonthAndProjectAndStationLike(month, project, station+"%");
//            if(stationCostCell == null) {
//                if(station.length()>3) {
//                    station = station.substring(0, 3);
//                }
//                stationCostCell = stationCostCellRepository.findFirstByMonthAndProjectAndStationLike(month, project, station+"%");
//                if(stationCostCell == null) {
//                    if(station.length()>2) {
//                        station = station.substring(0, 2);
//                    }
//                    stationCostCell = stationCostCellRepository.findFirstByMonthAndProjectAndStationLike(month, project, station+"%");
//                }
//            }
//            if(stationCostCell == null) {
//                return null;
//            } else {
//                return stationCostCell.getStation();
//            }
//        } else if(PlantEnum.Lcm.getPlant().equals(factory)) {
//            return null;
//        } else {
//            return null;
//        }
//    }
//
//
//    @Override
//    public String matchStationLcm(String project, String station, String product) {
//        String month = "2021-03";
//        if(StringUtils.isEmpty(station)) return null;
//
//        StationCostLcm stationCostLcm = stationCostLcmRepository.findFirstByMonthAndProjectAndProductAndStationLike(month, project, product, station+"%");
//        if(stationCostLcm == null) {
//            if(station.length()>3) {
//                station = station.substring(0, 3);
//            }
//            stationCostLcm = stationCostLcmRepository.findFirstByMonthAndProjectAndProductAndStationLike(month, project, product, station+"%");
//            if(stationCostLcm == null) {
//                if(station.length()>2) {
//                    station = station.substring(0, 2);
//                }
//                stationCostLcm = stationCostLcmRepository.findFirstByMonthAndProjectAndProductAndStationLike(month, project, product, station+"%");
//            }
//        }
//        if(stationCostLcm == null) {
//            return null;
//        } else {
//            return stationCostLcm.getStation();
//        }
//    }
//}
