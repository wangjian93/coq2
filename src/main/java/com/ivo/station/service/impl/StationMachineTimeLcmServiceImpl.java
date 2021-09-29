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
import java.util.Map;

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

    public Map matchMatnrAndMonth(String month, String product) {
        List<Map> mapList = stationMachineTimeLcmRepository.matchMatnrAndMonth(month, product+"%");
        if(mapList == null || mapList.size() == 0) {
            if(product.length()>=15)  {
                product = product.substring(0, 12);
                mapList = stationMachineTimeLcmRepository.matchMatnrAndMonth(month, product + "%");
            }
        }
        if(mapList == null || mapList.size() == 0) return null;
        return  mapList.get(0);
    }

    @Override
    public Double getPerProductAmountLcm(String month, String product) {
        if(StringUtils.isEmpty(product)) return null;
        //如何当月没有维护数据查取最近一个月的数据
        Map map = matchMatnrAndMonth(month, product);
        if(map == null) return null;
        month = (String) map.get("month");
        product = (String) map.get("matnr");
        return stationMachineTimeLcmRepository.getPerProductAmountLcm(month, product);
    }

    @Override
    public Double getPerScrapAmountLcm(String month, String product, String station) {
        if(StringUtils.isEmpty(product)) return null;
        //如何当月没有维护数据查取最近一个月的数据
        Map map = matchMatnrAndMonth(month, product);
        if(map == null) return null;
        month = (String) map.get("month");
        product = (String) map.get("matnr");
        StationMachineTimeLcm stationMachineTimeLcm = stationMachineTimeLcmRepository.findFirstByMonthAndMatnrAndStationLike(month, product, station+'%');
        //站点匹配不上截取前三码匹配，再不行截取前两码
        if(stationMachineTimeLcm == null) {
            if(station.length()>3) station = station.substring(0,3);
            stationMachineTimeLcm = stationMachineTimeLcmRepository.findFirstByMonthAndMatnrAndStationLike(month, product, station+'%');
            if(stationMachineTimeLcm == null) {
                if(station.length()>2) station = station.substring(0,2);
                stationMachineTimeLcm = stationMachineTimeLcmRepository.findFirstByMonthAndMatnrAndStationLike(month, product, station+'%');
            }
        }
        return stationMachineTimeLcm == null ? null : stationMachineTimeLcm.getAmount();
    }

    @Override
    public Double getPerReworkAmountLcm(String month, String product, String station) {
        if(StringUtils.isEmpty(product)) return null;
        //如何当月没有维护数据查取最近一个月的数据
        Map map = matchMatnrAndMonth(month, product);
        if(map == null) return null;
        month = (String) map.get("month");
        product = (String) map.get("matnr");
        return stationMachineTimeLcmRepository.getPerReworkAmountLcm(month, product, station);
    }
}
