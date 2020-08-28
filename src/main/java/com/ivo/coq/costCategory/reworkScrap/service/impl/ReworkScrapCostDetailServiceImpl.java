package com.ivo.coq.costCategory.reworkScrap.service.impl;

import com.ivo.common.enums.PlantEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostArray;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostCell;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostLcm;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostArrayRepository;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostCellRepository;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostLcmRepository;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostDetailService;
import com.ivo.coq.station.service.StationCostShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private StationCostShareService stationCostShareService;

    @Autowired
    public ReworkScrapCostDetailServiceImpl(ReworkScrapCostArrayRepository reworkScrapCostArrayRepository,
                                            ReworkScrapCostCellRepository reworkScrapCostCellRepository,
                                            ReworkScrapCostLcmRepository reworkScrapCostLcmRepository,
                                            StationCostShareService stationCostShareService) {
        this.reworkScrapCostArrayRepository = reworkScrapCostArrayRepository;
        this.reworkScrapCostCellRepository = reworkScrapCostCellRepository;
        this.reworkScrapCostLcmRepository = reworkScrapCostLcmRepository;
        this.stationCostShareService = stationCostShareService;
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
            String project_ = array.getProject().split(" ")[0];
            String factory = PlantEnum.Array.getPlant();
            String opeId = array.getOpeId();
            if(StringUtils.isEmpty(opeId)) continue;
            String station = stationCostShareService.matchStation(project_, factory, opeId);
            if(StringUtils.isEmpty(station)) {
                array.setStation(null);
                array.setStationAmount(null);
                array.setAmount(null);
            } else {
                array.setStation(station);
                if(StringUtils.equalsIgnoreCase(array.getEvtCate(), "Rework")) {
                    array.setStationAmount(stationCostShareService.getReworkAmount(project_, factory, station));
                } else {
                    array.setStationAmount(stationCostShareService.getScrapAmount(project_, factory, station));
                }
                array.setAmount(DoubleUtil.multiply(array.getQty(), array.getStationAmount()));
            }

        }
        reworkScrapCostArrayRepository.saveAll(arrayList);

        // 2.cell
        List<ReworkScrapCostCell> cellList = getReworkScrapCostCell(project);
        for(ReworkScrapCostCell cell : cellList) {
            String project_ = cell.getProject().split(" ")[0];
            String factory = PlantEnum.Cell.getPlant();
            String opeId = cell.getNxOpeId();
            if(StringUtils.isEmpty(opeId)) continue;
            String station = stationCostShareService.matchStation(project_, factory, opeId);
            if(StringUtils.isEmpty(station)) {
                cell.setStation(null);
                cell.setStationAmount(null);
                cell.setAmount(null);
            } else{
                cell.setStation(station);
                if(StringUtils.equalsIgnoreCase(cell.getEvtCate(), "Rework")) {
                    cell.setStationAmount(stationCostShareService.getReworkAmount(project_, factory, station));
                } else {
                    cell.setStationAmount(stationCostShareService.getScrapAmount(project_, factory, station));
                }
                cell.setAmount(DoubleUtil.multiply(cell.getQty(), cell.getStationAmount()));
            }
        }
        reworkScrapCostCellRepository.saveAll(cellList);

        // 3.lcm
        List<ReworkScrapCostLcm> lcmList = getReworkScrapCostLcm(project);
        for(ReworkScrapCostLcm lcm : lcmList) {
            String project_ = lcm.getProject().split(" ")[0];
            String factory = PlantEnum.Lcm.getPlant();
            String opeId = lcm.getCrOpeId();
            // 如果站点时7900要往前推站点
            if(StringUtils.equals(opeId, "7900")) {
                opeId = lcm.getScrpMOpeId();
            }
            if(StringUtils.isEmpty(opeId)) continue;
            String station = stationCostShareService.matchStation(project_, factory, opeId);
            if(StringUtils.isEmpty(station)) {
                lcm.setStation(null);
                lcm.setStationAmount(null);
                lcm.setAmount(null);
            } else {
                lcm.setStation(station);
                if(StringUtils.equalsIgnoreCase(lcm.getEvtCate(), "Rework")) {
                    lcm.setStationAmount(stationCostShareService.getReworkAmount(project_, factory, station));
                } else {
                    lcm.setStationAmount(stationCostShareService.getScrapAmount(project_, factory, station));
                }
                lcm.setAmount(DoubleUtil.multiply(lcm.getQty(), lcm.getStationAmount()));
            }
        }
        reworkScrapCostLcmRepository.saveAll(lcmList);
    }
}
