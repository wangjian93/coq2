package com.ivo.test.station;

import com.ivo.station.service.StationCycleTimeCellService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class StationCycleTimeCellServiceTest extends AbstractTest {

    @Autowired
    private StationCycleTimeCellService stationCycleTimeCellService;

    @Test
    public void syncStationCycleTimeCell() {
        stationCycleTimeCellService.syncStationCycleTimeCell("202105");
    }
}
