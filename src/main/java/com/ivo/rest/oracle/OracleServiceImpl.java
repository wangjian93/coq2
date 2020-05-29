package com.ivo.rest.oracle;

import com.ivo.coq.report.entity.*;
import com.ivo.rest.oracle.entity.OracleReworkScrapArray;
import com.ivo.rest.oracle.entity.OracleReworkScrapCell;
import com.ivo.rest.oracle.entity.OracleReworkScrapLcm;
import com.ivo.rest.oracle.mapper.OracleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class OracleServiceImpl implements OracleService {

    private OracleMapper oracleMapper;

    @Autowired
    public OracleServiceImpl(OracleMapper oracleMapper) {
        this.oracleMapper = oracleMapper;
    }

    @Override
    public Double getProductPerAmount(String product) {
        return oracleMapper.getProductPerAmount(product);
    }

    @Override
    public Double getWoAmount(String wo) {
        return oracleMapper.getWoAmount(wo);
    }

    @Override
    public Double getMaterialPrice(String material) {
        return oracleMapper.getMaterialPrice(material);
    }

    @Override
    public List<OracleReworkScrapArray> getOracleReworkScrapArray(Date fromDate, Date toDate, String prodId) {
        log.info("获取ARRAY的重工报废数据 " + prodId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return oracleMapper.getOracleReworkScrapArray(sdf.format(fromDate), sdf.format(toDate), prodId);
    }

    @Override
    public List<OracleReworkScrapCell> getOracleReworkScrapCell(Date fromDate, Date toDate, String prodId, String tft, String cf) {
        log.info("获取CELL的重工报废数据 " + prodId+"/"+tft+"/"+cf);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return oracleMapper.getOracleReworkScrapCell(sdf.format(fromDate), sdf.format(toDate), prodId, tft, cf);
    }

    @Override
    public List<OracleReworkScrapLcm> getOracleReworkScrapLcm(Date fromDate, Date toDate, String wo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        log.info("获取LCM1的重工报废数据 " + wo);
        List<OracleReworkScrapLcm> lcm1 =  oracleMapper.getOracleReworkScrapLcm1(sdf.format(fromDate), sdf.format(toDate), wo);
        log.info("获取LCM1的重工报废数据 " + wo);
        List<OracleReworkScrapLcm> lcm2 = oracleMapper.getOracleReworkScrapLcm2(sdf.format(fromDate), sdf.format(toDate), wo);
        List<OracleReworkScrapLcm> list = new ArrayList<>();
        if(lcm1 != null) {
            list.addAll(lcm1);
        }
        if(lcm2 != null) {
            list.addAll(lcm2);
        }
        return list;
    }

    @Override
    public List<InLossAmount> getInLossAmountArrayCell() {
        return oracleMapper.getInLossAmountArrayCell();
    }

    @Override
    public List<InLossAmount> getInLossAmountArrayLcm() {
        return oracleMapper.getInLossAmountArrayLcm();
    }

    @Override
    public List<TotalAmount> getTotalAmount() {
        return oracleMapper.getTotalAmount();
    }

    @Override
    public List<InLossAmountDetailArrayCell> getInLossAmountDetailArrayCell() {
        return oracleMapper.getInLossAmountDetailArrayCell();
    }

    @Override
    public List<InLossAmountDetailLcm> getInLossAmountDetailLcm() {
        return oracleMapper.getInLossAmountDetailLcm();
    }

    @Override
    public Double getWoShippingQty(String wo) {
        return oracleMapper.getWoShippingQty(wo);
    }

    @Override
    public List<WbRatio> getWbRatio() {
        return oracleMapper.getWbRatio();
    }
}
