package com.ivo.rest.eifdb;

import com.ivo.rest.eifdb.entity.EifEngineeringExperimentMaterial;
import com.ivo.rest.eifdb.entity.EifEngineeringExperimentProduct;

import java.util.List;
import java.util.Map;

/**
 * EIF数据服务接口
 * @author wj
 * @version 1.0
 */
public interface EifService {

    /**
     * 根据ED单获取EE单工程试验单
     * @param ed ED单试验委托单
     * @return
     */
    List<String> getEeByEd(String ed);

    /**
     * 获取EE单的厂别（ARRAY/CELL/LCM）
     * @param ee 工程试验单
     * @return
     */
    Map getPlantForEE(String ee);

    /**
     * 获取EE单中的产品ID和实验数量 （ARRAY/CELL）
     * @param ee 工程试验单
     * @return
     */
    List<EifEngineeringExperimentProduct> getEifEngineeringExperimentProduct(String ee);

    /**
     * 获取EE单中的料号
     * @param ee 工程试验单 (CELL)
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
