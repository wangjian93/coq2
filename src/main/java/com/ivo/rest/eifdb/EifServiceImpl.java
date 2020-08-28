package com.ivo.rest.eifdb;

import com.ivo.rest.eifdb.entity.EifEngineeringExperimentMaterial;
import com.ivo.rest.eifdb.entity.EifEngineeringExperimentProduct;
import com.ivo.rest.eifdb.mapper.EifMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class EifServiceImpl implements EifService {

    private EifMapper eifMapper;

    @Autowired
    public EifServiceImpl(EifMapper eifMapper) {
        this.eifMapper = eifMapper;
    }

    @Override
    public List<String> getEeByEd(String ed) {
        log.info("数据接口：ED单获取EE单工程试验单 " + ed);
        return eifMapper.getEeByEd(ed);
    }

    @Override
    public String getPlantForEE(String ee) {
        log.info("数据接口：EE单的厂别 " + ee);
        return eifMapper.getPlantForEE(ee);
    }

    @Override
    public List<EifEngineeringExperimentProduct> getEifEngineeringExperimentProduct(String ee) {
        log.info("数据接口：EE单中的产品ID和实验数量 " + ee);
        return eifMapper.getEifEngineeringExperimentProduct(ee);
    }

    @Override
    public List<EifEngineeringExperimentMaterial> getEifEngineeringExperimentMaterial(String ee) {
        log.info("数据接口：EE单中的料号 " + ee);
        return eifMapper.getEifEngineeringExperimentMaterial(ee);
    }

    @Override
    public List<String> getEngineeringExperimentWo(String ee) {
        log.info("数据接口：EE单中的工单 " + ee);
        return eifMapper.getEngineeringExperimentWo(ee);
    }

    @Override
    public String getPrByOee(String oee) {
        log.info("数据接口：OEE获取到对应的PR " + oee);
        return eifMapper.getPrByOee(oee);
    }

    @Override
    public List<Map> getShipment(String project) {
        log.info("数据接口：获取机种的样品申请单的出货信息" + project);
        return eifMapper.getShipment(project);
    }
}
