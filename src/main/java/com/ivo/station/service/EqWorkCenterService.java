package com.ivo.station.service;

/**
 * @author wj
 * @version 1.0
 */
public interface EqWorkCenterService {

    /**
     * 获取ARRAY/CELL机台对应的成本中心
     * @param eq 机台
     * @param fab ARRAY/CELL
     * @return
     */
    String getCostCenterByEq(String eq, String fab);

    /**
     * 获取LCM工作中心对应成成本中心
     * @return
     */
    String getCostCenterByWorkCenter(String workCenter);


}
