package com.ivo.station.service.impl;

import com.ivo.station.entity.StationMachineTimeLcm;
import com.ivo.station.repository.StationMachineTimeLcmRepository;
import com.ivo.station.service.EqWorkCenterService;
import com.ivo.station.service.StationMachineTimeLcmService;
import com.ivo.station.service.WorkRateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class StationMachineTimeLcmServiceImpl implements StationMachineTimeLcmService {

    @Resource
    private StationMachineTimeLcmRepository stationMachineTimeLcmRepository;

    @Resource
    private WorkRateService workRateService;

    @Resource
    private EqWorkCenterService eqWorkCenterService;

    @Override
    public void computeLcmStationAmount() {
        List<StationMachineTimeLcm> stationMachineTimeLcmList = stationMachineTimeLcmRepository.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        for(StationMachineTimeLcm stationMachineTimeLcm : stationMachineTimeLcmList) {
            String month = sdf.format(stationMachineTimeLcm.getMes_cut_date());
            stationMachineTimeLcm.setMonth(month);
            String station = stationMachineTimeLcm.getWork_center().substring(4,8);
            stationMachineTimeLcm.setStation(station);

            String workCenter = stationMachineTimeLcm.getWork_center();
            String costCenter = eqWorkCenterService.getCostCenterByWorkCenter(workCenter);
            if(StringUtils.isEmpty(costCenter)) {
                stationMachineTimeLcm.setMemo("工作中心未匹配到成本中心");
                continue;
            }
            stationMachineTimeLcm.setCostCenter(costCenter);

            double workRate_m = workRateService.getM_price(costCenter);
            double workRate_other= workRateService.getOther_price(costCenter);
            if(workRate_m <= 0 && workRate_other<=0) {
                stationMachineTimeLcm.setMemo("未获取到单位费率");
                continue;
            }
            stationMachineTimeLcm.setWorkRate_m(workRate_m);
            stationMachineTimeLcm.setWorkRate_other(workRate_other);

            //生产/报废费用（单片）=人工工时/基础数量*人工费率+机器工时/基础数量*其他费率
            //重工费用（单片）=0
            double amount = stationMachineTimeLcm.getTime_m()/stationMachineTimeLcm.getBase_qty()*workRate_m +
                    stationMachineTimeLcm.getTime_p()/stationMachineTimeLcm.getBase_qty()*workRate_other;
            double reworkAmount = 0;
            stationMachineTimeLcm.setAmount(amount);
            stationMachineTimeLcm.setReworkAmount(reworkAmount);
        }
        stationMachineTimeLcmRepository.saveAll(stationMachineTimeLcmList);
    }


    @Override
    public Double getPerProductAmountLcm(String month, String product) {
        //如何当月没有维护数据查取最近一个月的数据
        if(stationMachineTimeLcmRepository.countByMonthAndMatnr(month, product)==0) {
            month = stationMachineTimeLcmRepository.getLastMonth(product);
        }
        if (month==null) return null;
        return stationMachineTimeLcmRepository.getPerProductAmountLcm(month, product);
    }

    @Override
    public Double getPerScrapAmountLcm(String month, String product, String station) {
        //如何当月没有维护数据查取最近一个月的数据
        if(stationMachineTimeLcmRepository.countByMonthAndMatnr(month, product)==0) {
            month = stationMachineTimeLcmRepository.getLastMonth(product);
        }
        if (month==null) return null;
        StationMachineTimeLcm stationMachineTimeLcm = stationMachineTimeLcmRepository.findFirstByMonthAndMatnrAndStationLike(month, product, station);
        return stationMachineTimeLcm == null ? null : stationMachineTimeLcm.getAmount();
    }

    @Override
    public Double getPerReworkAmountLcm(String month, String product, String station) {
        //如何当月没有维护数据查取最近一个月的数据
        if(stationMachineTimeLcmRepository.countByMonthAndMatnr(month, product)==0) {
            month = stationMachineTimeLcmRepository.getLastMonth(product);
        }
        if (month==null) return null;
        return stationMachineTimeLcmRepository.getPerReworkAmountLcm(month, product, station);
    }
}
