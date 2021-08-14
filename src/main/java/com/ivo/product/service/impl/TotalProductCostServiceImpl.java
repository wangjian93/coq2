package com.ivo.product.service.impl;

import com.ivo.product.entity.TotalProductCost;
import com.ivo.product.repository.TotalProductCostRepository;
import com.ivo.product.service.TotalProductCostService;
import com.ivo.rest.oracle.mapper.OracleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class TotalProductCostServiceImpl implements TotalProductCostService {

    @Resource
    private OracleMapper oracleMapper;

    @Resource
    private TotalProductCostRepository repository;

    @Override
    public void syncTotalProductCost() {
        List<Map> mapList = oracleMapper.getTotalProductCost();
        repository.deleteAll();
        List<TotalProductCost> list = new ArrayList<>();
        for(Map map : mapList) {
            TotalProductCost totalProductCost = new TotalProductCost();
            String fab = (String) map.get("FAB_ID");
            if(fab.equals("CEL")) {
                fab = "CELL";
            }
            if(fab.equals("ARY")) {
                fab = "ARRAY";
            }

            totalProductCost.setFab(fab);


            totalProductCost.setMonth((String) map.get("MON"));
            double d1 = 0;
            if(map.get("PRICE")!=null) {
                d1 = ((BigDecimal) map.get("PRICE")).doubleValue();
            }
            double d2 = 0;
            if(map.get("AMOUNT")!=null) {
                d2 = ((BigDecimal) map.get("AMOUNT")).doubleValue();
            }
            totalProductCost.setPrice(d1);
            totalProductCost.setAmount(d2);
            list.add(totalProductCost);
        }
        repository.saveAll(list);
    }
}
