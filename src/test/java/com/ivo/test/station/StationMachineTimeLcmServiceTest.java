package com.ivo.test.station;

import com.ivo.station.service.StationMachineTimeLcmService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
public class StationMachineTimeLcmServiceTest extends AbstractTest {

    @Autowired
    private StationMachineTimeLcmService stationMachineTimeLcmService;

    @Test
    public void computeLcmStationAmount() {
        stationMachineTimeLcmService.computeLcmStationAmount();
    }
}
