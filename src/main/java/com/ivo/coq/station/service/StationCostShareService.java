package com.ivo.coq.station.service;

import com.ivo.coq.station.entity.StationCostShare;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 单片机种经过站点分摊成本服务接口
 * @author wj
 * @version 1.0
 */
public interface StationCostShareService {

    /**
     * 获取单片机种经过站点的分摊费用
     * @param project 机种
     * @return List<StationCostShare>
     */
    List<StationCostShare> getStationCostShare(String project);

    /**
     * 获取站点之前经过的站点
     * @param project 机种
     * @param factory 产别
     * @param station 站点
     * @return List<StationCostShare>
     */
    List<StationCostShare> getBeforeStation(String project, String factory, String station);


    /**
     * 获取站点
     * @param project 机种
     * @param factory 产别
     * @param station 站点
     * @return StationCostShare
     */
    StationCostShare getStationCostShare(String project, String factory, String station);

    /**
     * 获取Array/cell/lcm厂的站点
     * @param project 机种
     * @param factory 厂
     * @return
     */
    List<StationCostShare> getStationCostShare(String project, String factory);

    /**
     * 获取机种经过站点的单片报废成本
     * @param project 机种
     * @param factory 厂别
     * @param station 站点
     * @return Double
     */
    Double getScrapAmount(String project, String factory, String station);

    /**
     * 获取机种经过站点的单片重工费用
     * @param project 机种
     * @param factory 厂别
     * @param station 站点
     * @return Double
     */
    Double getReworkAmount(String project, String factory, String station);

    /**
     * 获取机种经过Array/cell/lcm厂的的单片生产费用
     * @param factory 厂别
     * @return Double
     */
    Double getProductAmount(String project, String factory);

    /**
     * excel导入站点分摊成本
     * @param excel EXCEL
     * @param fileName EXCEL文件文
     */
    void importStationCostShare(InputStream excel, String fileName) throws IOException;

    /**
     * 匹配站点
     * @param project 机种
     * @param factory 厂
     * @param station 站点
     * @return
     */
    String matchStation(String project, String factory, String station);
}
