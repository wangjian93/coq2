package com.ivo.station.service.impl;

import com.ivo.station.service.StationCostService;
import com.ivo.station.service.StationCycleTimeAryService;
import com.ivo.station.service.StationCycleTimeCellService;
import com.ivo.station.service.StationMachineTimeLcmService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class StationCostServiceImpl2 implements StationCostService {

    @Resource
    private StationCycleTimeCellService stationCycleTimeCellService;

    @Resource
    private StationCycleTimeAryService stationCycleTimeAryService;

    @Resource
    private StationMachineTimeLcmService stationMachineTimeLcmService;

    @Override
    @Cacheable(value = {"getPerProductAmountAry"})
    public double getPerProductAmountAry(String month, String project) {
        Double d = stationCycleTimeAryService.getPerProductAmountAry(month, project);
        return d==null ? 0 : d;
    }

    @Override
    @Cacheable(value = {"getPerProductAmountCell"})
    public double getPerProductAmountCell(String month, String project, String prod_id) {
        Double d = stationCycleTimeCellService.getPerProductAmountCell(month, project, prod_id);
        return d==null ? 0 : d;
    }

    @Override
    @Cacheable(value = {"getPerProductAmountLcm"})
    public double getPerProductAmountLcm(String month, String product) {
        Double d = stationMachineTimeLcmService.getPerProductAmountLcm(month, product);
        return d==null ? 0 : d;
    }

    @Override
    @Cacheable(value = {"getPerScrapAmountAry"})
    public double getPerScrapAmountAry(String month, String project, String station) {
        Double d = stationCycleTimeAryService.getPerScrapAmount(month, project, station);
        return d==null ? 0 : d;
    }

    @Override
    @Cacheable(value = {"getPerScrapAmountCell"})
    public double getPerScrapAmountCell(String month, String project, String station, String prod_id) {
        Double d = stationCycleTimeCellService.getPerScrapAmountCell(month, project, station, prod_id);
        return d==null ? 0 : d;
    }

    @Override
    @Cacheable(value = {"getPerScrapAmountLcm"})
    public double getPerScrapAmountLcm(String month, String product, String station) {
        Double d = stationMachineTimeLcmService.getPerScrapAmountLcm(month, product, station);
        return d==null ? 0 : d;
    }

    @Override
    @Cacheable(value = {"getPerReworkAmountAry"})
    public double getPerReworkAmountAry(String month, String project, String station) {
        Double d = stationCycleTimeAryService.getPerReworkAmountAry(month, project, station);
        return d==null ? 0 : d;
    }

    @Override
    @Cacheable(value = {"getPerReworkAmountCell"})
    public double getPerReworkAmountCell(String month, String project, String station, String prod_id) {
        Double d = stationCycleTimeCellService.getPerReworkAmountCell(month, project, station, prod_id);
        return d==null ? 0 : d;
    }

    @Override
    @Cacheable(value = {"getPerReworkAmountLcm"})
    public double getPerReworkAmountLcm(String month, String product, String station) {
        Double d = stationMachineTimeLcmService.getPerReworkAmountLcm(month, product, station);
        return d==null ? 0 : d;
    }
}
