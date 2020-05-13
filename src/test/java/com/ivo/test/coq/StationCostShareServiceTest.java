package com.ivo.test.coq;

import com.ivo.coq.station.service.StationCostShareService;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wj
 * @version 1.0
 */
public class StationCostShareServiceTest extends AbstractTest {

    @Autowired
    private StationCostShareService stationCostShareService;

    private static final String path = "/Users/wangjian/Downloads/N1568V站点.xlsx";

    @Test
    public void a1_importStationCostShare() throws IOException {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        stationCostShareService.importStationCostShare(inputStream, "N1568V站点.xlsx");
    }

    @Test
    public void a2_getProductAmount() {
        System.out.println(stationCostShareService.getProductAmount("N1568V", "Array"));
        System.out.println(stationCostShareService.getProductAmount("N1568V", "Cell"));
        System.out.println(stationCostShareService.getProductAmount("N1568V", "LCM"));
    }

    @Test
    public void a3_getReworkAmount() {
        System.out.println(stationCostShareService.getReworkAmount("N1568V", "Array", "1200"));
    }

    @Test
    public void a4_getScrapAmount() {
        System.out.println(stationCostShareService.getScrapAmount("N1568V", "Array", "1200"));
    }


}
