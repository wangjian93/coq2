package com.ivo.coq.costCategory.reworkScrap.service.impl;

import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostArray;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostCell;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostLcm;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostArrayRepository;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostCellRepository;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostLcmRepository;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostDetailService;
import com.ivo.station.service.StationCostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class ReworkScrapCostDetailServiceImpl implements ReworkScrapCostDetailService {

    private ReworkScrapCostArrayRepository reworkScrapCostArrayRepository;

    private ReworkScrapCostCellRepository reworkScrapCostCellRepository;

    private ReworkScrapCostLcmRepository reworkScrapCostLcmRepository;


    @Resource
    private StationCostService stationCostService;

    @Autowired
    public ReworkScrapCostDetailServiceImpl(ReworkScrapCostArrayRepository reworkScrapCostArrayRepository,
                                            ReworkScrapCostCellRepository reworkScrapCostCellRepository,
                                            ReworkScrapCostLcmRepository reworkScrapCostLcmRepository) {
        this.reworkScrapCostArrayRepository = reworkScrapCostArrayRepository;
        this.reworkScrapCostCellRepository = reworkScrapCostCellRepository;
        this.reworkScrapCostLcmRepository = reworkScrapCostLcmRepository;
    }

    @Override
    public List<ReworkScrapCostArray> getReworkScrapCostArray(String project) {
        return reworkScrapCostArrayRepository.findByProject(project);
    }

    @Override
    public List<ReworkScrapCostCell> getReworkScrapCostCell(String project) {
        return reworkScrapCostCellRepository.findByProject(project);
    }

    @Override
    public List<ReworkScrapCostLcm> getReworkScrapCostLcm(String project) {
        return reworkScrapCostLcmRepository.findByProject(project);
    }

    @Override
    public List<ReworkScrapCostArray> getReworkScrapCostArray(String project, String stage, Integer time) {
        return reworkScrapCostArrayRepository.findByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public List<ReworkScrapCostCell> getReworkScrapCostCell(String project, String stage, Integer time) {
        return reworkScrapCostCellRepository.findByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public List<ReworkScrapCostLcm> getReworkScrapCostLcm(String project, String stage, Integer time) {
        return reworkScrapCostLcmRepository.findByProjectAndStageAndTime(project, stage, time);
    }

    @Override
    public void saveArray(List<ReworkScrapCostArray> list) {
        reworkScrapCostArrayRepository.saveAll(list);
    }

    @Override
    public void saveCell(List<ReworkScrapCostCell> list) {
        reworkScrapCostCellRepository.saveAll(list);
    }

    @Override
    public void saveLcm(List<ReworkScrapCostLcm> list) {
        reworkScrapCostLcmRepository.saveAll(list);
    }

    @Override
    public void deleteArray(Long jobId) {
        reworkScrapCostArrayRepository.deleteAll(reworkScrapCostArrayRepository.findByJobId(jobId));
    }

    @Override
    public void deleteCell(Long jobId) {
        reworkScrapCostCellRepository.deleteAll(reworkScrapCostCellRepository.findByJobId(jobId));
    }

    @Override
    public void deleteLcm(Long jobId) {
        reworkScrapCostLcmRepository.deleteAll(reworkScrapCostLcmRepository.findByJobId(jobId));
    }

    @Override
    public void computeReworkScrapCostDetail(String project) {
        log.info("计算Array、cell、lcm三厂的重工报废费用明细 " + project);
//        reworkScrapCostArrayRepository.deleteAll(getReworkScrapCostArray(project));
//        reworkScrapCostCellRepository.deleteAll(getReworkScrapCostCell(project));
//        reworkScrapCostLcmRepository.deleteAll(getReworkScrapCostLcm(project));

        // 1.array
        List<ReworkScrapCostArray> arrayList = getReworkScrapCostArray(project);
        for(ReworkScrapCostArray array : arrayList) {
            String opeId = array.getOpeId();
            if(StringUtils.isEmpty(opeId)) continue;
            String station = opeId;
            if(StringUtils.equalsIgnoreCase(array.getEvtCate(), "Rework")) {
                double perReworkAmount = stationCostService.getPerReworkAmountAry(project, null, station);

                // 匹配不到站点时，匹配前三码，还没有然后再前两码
                if(perReworkAmount == 0) {
                    station = station.substring(0, 3);
                    perReworkAmount = stationCostService.getPerReworkAmountAry(project, null, station);
                    if(perReworkAmount == 0) {
                        station = station.substring(0, 2);
                        perReworkAmount = stationCostService.getPerReworkAmountAry(project, null, station);
                    }
                }

                if(perReworkAmount == 0) {
                    array.setStation(null);
                    array.setStationAmount(null);
                    array.setAmount(null);
                } else {
                    array.setStation(station);
                    array.setStationAmount(perReworkAmount);
                    array.setAmount(DoubleUtil.multiply(array.getQty(), perReworkAmount));
                }
            } else {
                double perScrapAmount = stationCostService.getPerScrapAmountAry(project, null, station);

                // 匹配不到站点时，匹配前三码，还没有然后再前两码
                if(perScrapAmount == 0) {
                    station = station.substring(0, 3);
                    perScrapAmount = stationCostService.getPerScrapAmountAry(project, null, station);
                    if(perScrapAmount == 0) {
                        station = station.substring(0, 2);
                        perScrapAmount = stationCostService.getPerScrapAmountAry(project, null, station);
                    }
                }

                if(perScrapAmount == 0) {
                    array.setStation(null);
                    array.setStationAmount(null);
                    array.setAmount(null);
                } else {
                    array.setStation(station);
                    array.setStationAmount(perScrapAmount);
                    array.setAmount(DoubleUtil.multiply(array.getQty(), perScrapAmount));
                }
            }
        }
        reworkScrapCostArrayRepository.saveAll(arrayList);

        // 2.cell
        List<ReworkScrapCostCell> cellList = getReworkScrapCostCell(project);
        for(ReworkScrapCostCell cell : cellList) {
            String opeId = cell.getNxOpeId();
            if(StringUtils.isEmpty(opeId)) continue;
            String station = opeId;
            if(StringUtils.equalsIgnoreCase(cell.getEvtCate(), "Rework")) {
                double perReworkAmount = stationCostService.getPerReworkAmountCell(project, null, station, "");

                // 匹配不到站点时，匹配前三码，还没有然后再前两码
                if(perReworkAmount == 0) {
                    station = station.substring(0, 3);
                    perReworkAmount = stationCostService.getPerReworkAmountCell(project, null, station, "");
                    if(perReworkAmount == 0) {
                        station = station.substring(0, 2);
                        perReworkAmount = stationCostService.getPerReworkAmountCell(project, null, station, "");
                    }
                }

                if(perReworkAmount == 0) {
                    cell.setStation(null);
                    cell.setStationAmount(null);
                    cell.setAmount(null);
                } else {
                    cell.setStation(station);
                    cell.setStationAmount(perReworkAmount);
                    cell.setAmount(DoubleUtil.multiply(cell.getQty(), perReworkAmount));
                }
            } else {
                double perScrapAmount = stationCostService.getPerScrapAmountCell(project, null, station, "");

                // 匹配不到站点时，匹配前三码，还没有然后再前两码
                if(perScrapAmount == 0) {
                    station = station.substring(0, 3);
                    perScrapAmount = stationCostService.getPerScrapAmountCell(project, null, station, "");
                    if(perScrapAmount == 0) {
                        station = station.substring(0, 2);
                        perScrapAmount = stationCostService.getPerScrapAmountCell(project, null, station, "");
                    }
                }

                if(perScrapAmount == 0) {
                    cell.setStation(null);
                    cell.setStationAmount(null);
                    cell.setAmount(null);
                } else {
                    cell.setStation(station);
                    cell.setStationAmount(perScrapAmount);
                    cell.setAmount(DoubleUtil.multiply(cell.getQty(), perScrapAmount));
                }
            }
        }
        reworkScrapCostCellRepository.saveAll(cellList);

        // 3.lcm
        List<ReworkScrapCostLcm> lcmList = getReworkScrapCostLcm(project);
        for(ReworkScrapCostLcm lcm : lcmList) {
            String opeId = lcm.getCrOpeId();
            if(StringUtils.isEmpty(opeId)) continue;
            String station = opeId;
            String product = lcm.getProductId();
            if(StringUtils.equalsIgnoreCase(lcm.getEvtCate(), "Rework")) {
                double perReworkAmount = stationCostService.getPerReworkAmountLcm( product, null, station);

                // 匹配不到站点时，匹配前三码，还没有然后再前两码
                if(perReworkAmount == 0) {
                    station = station.substring(0, 3);
                    perReworkAmount = stationCostService.getPerReworkAmountLcm( product,null, station);
                    if(perReworkAmount == 0) {
                        station = station.substring(0, 2);
                        perReworkAmount = stationCostService.getPerReworkAmountLcm( product,null, station);
                    }
                }

                if(perReworkAmount == 0) {
                    lcm.setStation(null);
                    lcm.setStationAmount(null);
                    lcm.setAmount(null);
                } else {
                    lcm.setStation(station);
                    lcm.setStationAmount(perReworkAmount);
                    lcm.setAmount(DoubleUtil.multiply(lcm.getQty(), perReworkAmount));
                }
            } else {
                double perScrapAmount = stationCostService.getPerScrapAmountLcm( product,null, station);

                // 匹配不到站点时，匹配前三码，还没有然后再前两码
                if(perScrapAmount == 0) {
                    station = station.substring(0, 3);
                    perScrapAmount = stationCostService.getPerScrapAmountLcm( product,null, station);
                    if(perScrapAmount == 0) {
                        station = station.substring(0, 2);
                        perScrapAmount = stationCostService.getPerScrapAmountLcm( product,null, station);
                    }
                }

                if(perScrapAmount == 0) {
                    lcm.setStation(null);
                    lcm.setStationAmount(null);
                    lcm.setAmount(null);
                } else {
                    lcm.setStation(station);
                    lcm.setStationAmount(perScrapAmount);
                    lcm.setAmount(DoubleUtil.multiply(lcm.getQty(), perScrapAmount));
                }
            }
        }
        reworkScrapCostLcmRepository.saveAll(lcmList);
    }
}
