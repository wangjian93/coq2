package com.ivo.rest.eifdb.mapper;

import com.ivo.rest.eifdb.entity.EifEngineeringExperimentMaterial;
import com.ivo.rest.eifdb.entity.EifEngineeringExperimentProduct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
@Repository
public interface EifMapper {

    /**
     * 根据ED单查询出EE单
     * @param ed 实验委托单
     * @return
     */
    List<String> getEeByEd(String ed);

    /**
     * 获取EE单厂别
     * @param ee 工程实验单
     * @return
     */
    String getPlantForEE(String ee);

    /**
     * 获取EE单（Array/Cell）中的产品
     * @param ee  工程实验单
     * @return
     */
    List<EifEngineeringExperimentProduct> getEifEngineeringExperimentProduct(String ee);

    /**
     * 获取EE单（CELL）中的料号
     * @param ee 工程实验单
     * @return
     */
    List<EifEngineeringExperimentMaterial> getEifEngineeringExperimentMaterial(String ee);

    /**
     * 获取EE单（LCM）中的工单
     * @param ee 工程实验单
     * @return
     */
    List<String> getEngineeringExperimentWo(String ee);

    /**
     * 根据OEE获取到对应的PR
     * @param oee 外包制程工程实验单
     * @return PR单号
     */
    String getPrByOee(String oee);

    /**
     * 从样品申请单获取出货信息
     * @param project 机种
     * @return List<Map>
     */
    List<Map> getShipment(String project);
}

