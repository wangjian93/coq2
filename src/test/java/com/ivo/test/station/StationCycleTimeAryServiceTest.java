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
    public void syncStationCycleTime() {
        System.out.println(stationCycleTimeAryService.getPerReworkAmountAry("202105", "N1166", "4505"));
    }
}
