package com.ivo.rest.oracle;

import com.ivo.rest.oracle.mapper.OracleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wj
 * @version 1.0
 */
@Service
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
}
