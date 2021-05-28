package com.ivo.test.station;

import com.ivo.station.service.StationCycleTimeAryService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class StationCycleTimeAryServiceTest extends AbstractTest {

    @Autowired
    private StationCycleTimeAryService stationCycleTimeAryService;

    @Test
    public void computeAryStationAmount() {
        stationCycleTimeAryService.computeAryStationAmount("202104");
    }

    @Test
    public void sumStationCycleTime() {
        stationCycleTimeAryService.sumStationCycleTime("202104");
    }

    @Test
    public void t() {
        double d = stationCycleTimeAryService.getPerProductAmountAry("202104", "N1568V999999");
    }
}
