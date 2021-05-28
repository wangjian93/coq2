package com.ivo.test.coq.station;

import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostArray;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostCell;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostLcm;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapSyncJob;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostArrayRepository;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostCellRepository;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapCostLcmRepository;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapSyncJobRepository;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapSyncJobService;
import com.ivo.coq.project.entity.EngineeringExperimentWo;
import com.ivo.coq.project.repository.EngineeringExperimentWoRepository;
import com.ivo.coq.station.service.StationCostAryService;
import com.ivo.coq.station.service.StationCostCellService;
import com.ivo.rest.oracle.OracleService;
import com.ivo.test.AbstractTest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public class Test extends AbstractTest {

    @Autowired
    public StationCostAryService stationCostAryService;

    @Autowired
    public StationCostCellService stationCostCellService;

    @Autowired
    public OracleService oracleService;

    @Autowired
    public EngineeringExperimentWoRepository engineeringExperimentWoRepository;

    @Autowired
    public ReworkScrapSyncJobRepository reworkScrapSyncJobRepository;

    @Autowired
    public ReworkScrapCostArrayRepository arrayRepository;

    @Autowired
    public ReworkScrapCostCellRepository cellRepository;

    @Autowired
    public ReworkScrapCostLcmRepository lcmRepository;

    @org.junit.Test
    public void test() {
        String project1 = "N1568V";
        String project2 = "A0906";

        stationCostAryService.computeStationCostAry(project1);
        stationCostCellService.computeStationCostCell(project1);

        stationCostAryService.computeStationCostAry(project2);
        stationCostCellService.computeStationCostCell(project2);
    }

    @org.junit.Test
    public void test2() {
        List<EngineeringExperimentWo> list = engineeringExperimentWoRepository.findByProject("N1568V R0");
        for(EngineeringExperimentWo ew : list) {
            String wo = ew.getWo();
            String product = oracleService.getProductByWo(wo);
            ew.setProduct(product);
            engineeringExperimentWoRepository.save(ew);
        }
    }

    @org.junit.Test
    public void test3() {
        List<ReworkScrapSyncJob> list = reworkScrapSyncJobRepository.findByProject("N1568V R0");
        for(ReworkScrapSyncJob job : list) {
            if(StringUtils.isNotEmpty(job.getWo())) {
                job.setWo_product(oracleService.getProductByWo(job.getWo()));
                reworkScrapSyncJobRepository.save(job);
            }

            List<ReworkScrapCostArray> list1 = arrayRepository.findByJobId(job.getId());
            List<ReworkScrapCostCell> list2 = cellRepository.findByJobId(job.getId());
            List<ReworkScrapCostLcm> list3 = lcmRepository.findByJobId(job.getId());
            for(ReworkScrapCostArray array : list1) {
                array.setInDate(job.getInDate());
            }
            arrayRepository.saveAll(list1);
            for(ReworkScrapCostCell cell : list2) {
                cell.setInDate(job.getInDate());
            }
            cellRepository.saveAll(list2);
            for(ReworkScrapCostLcm lcm : list3) {
                lcm.setInDate(job.getInDate());
                lcm.setWo(job.getWo());
                lcm.setProductId(job.getWo_product());
            }
            lcmRepository.saveAll(list3);

        }

    }



}
