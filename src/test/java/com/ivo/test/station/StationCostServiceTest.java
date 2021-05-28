package com.ivo.test.station;

import com.ivo.station.service.StationCostService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class StationCostServiceTest extends AbstractTest {

    @Autowired
    private StationCostService stationCostService;

    @Test
    public void test() {
        String month = "202104";
        String project = "N1568V";
        String pfcd = "E156TDHCT0";
        String product = "5135NV41-001M11";
        String station1 = "1104";
        String station2 = "0500";
        String station3 = "1000";
        double d1 = stationCostService.getPerProductAmountAry(month, project);
        double d2 = stationCostService.getPerProductAmountCell(month, project, pfcd);
        double d3 = stationCostService.getPerProductAmountLcm(month, product);

        double d4 = stationCostService.getPerScrapAmountAry(month, project, station1);
        double d5 = stationCostService.getPerScrapAmountCell(month, project, station2, pfcd);
        double d6 = stationCostService.getPerScrapAmountLcm(month, product, station3);

        double d7 = stationCostService.getPerReworkAmountAry(month, project, station1);
        double d8 = stationCostService.getPerReworkAmountCell(month, project, station2, pfcd);
        double d9 = stationCostService.getPerReworkAmountLcm(month, product, station3);

        d9=0;
    }
}
