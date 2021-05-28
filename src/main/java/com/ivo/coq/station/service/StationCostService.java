package com.ivo.coq.station.service;

/**
 * @author wj
 * @version 1.0
 */
public interface StationCostService {

    double getPerProductAmountAry(String project);

    double getPerProductAmountCell(String project);

    double getPerProductAmountLcm(String project, String wo);


    double getProductAmountLcm(String project, String wo);



    double getPerReworkAmountAry(String project, String station);

    double getPerReworkAmountCell(String project, String station);


    double getPerScrapAmountAry(String project, String station);

    double getPerScrapAmountCell(String project, String station);

    double getPerScrapAmountLcm(String project, String station, String product);

    String matchStation(String project, String factory, String station);

    String matchStationLcm(String project, String station, String product);

}
