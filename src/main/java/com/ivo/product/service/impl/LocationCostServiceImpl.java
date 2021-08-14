package com.ivo.product.service.impl;

import com.ivo.product.entity.MSC_LOCATION_COST;
import com.ivo.product.entity.WMM_SCRAP_DETAIL;
import com.ivo.product.repository.LocationCostRepository;
import com.ivo.product.repository.ScrapDetailRepository;
import com.ivo.product.service.LocationCostService;
import com.ivo.rest.oracle.mapper.OracleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class LocationCostServiceImpl implements LocationCostService {


    @Resource
    private LocationCostRepository locationCostRepository;

    @Resource
    private ScrapDetailRepository scrapDetailRepository;

    @Resource
    private OracleMapper oracleMapper;

    @Override
    public void syncLocationCost() {
        locationCostRepository.deleteAll();
        List<Map> mapList = oracleMapper.MSC_LOCATION_COST();
        List<MSC_LOCATION_COST> list = new ArrayList<>();
        for(Map map : mapList) {
            MSC_LOCATION_COST m = new MSC_LOCATION_COST();
            m.setFab((String) map.get("FAB_ID"));
            m.setDate(new Date(((Timestamp) map.get("DATE")).getTime()));
            m.setProd_id((String) map.get("PROD_ID"));
            m.setVer_id((String) map.get("VER_ID"));
            m.setPrice(((BigDecimal) map.get("PRICE")).doubleValue());
            m.setAmount(((BigDecimal) map.get("AMOUNT")).doubleValue());
            list.add(m);
        }
        locationCostRepository.saveAll(list);
    }

    @Override
    public void syncScrapDetail() {
        scrapDetailRepository.deleteAll();
        List<Map> mapList = oracleMapper.WMM_SCRAP_DETAIL();
        List<WMM_SCRAP_DETAIL> list = new ArrayList<>();
        for(Map map : mapList) {
            WMM_SCRAP_DETAIL m = new WMM_SCRAP_DETAIL();
            m.setFabDate(new Date(((Timestamp) map.get("FAB_DATE")).getTime()));
            m.setMon((String) map.get("MON"));
            m.setTrack_num((String) map.get("TRACK_NUM"));
            m.setMaterial_fk((String) map.get("MATERIAL_FK"));
            m.setFab((String) map.get("FAB_ID"));
            list.add(m);
        }
        scrapDetailRepository.saveAll(list);
    }
}
