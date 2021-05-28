package com.ivo.test.coq;

import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapSyncJobService;
import com.ivo.test.AbstractTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wj
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReworkScrapSyncJobServiceTest extends AbstractTest {

    @Autowired
    private ReworkScrapSyncJobService reworkScrapSyncJobService;

    @Test
    public void a1_generateJob() {
        reworkScrapSyncJobService.generateJob("N1568V R0");
        reworkScrapSyncJobService.generateJob("A0906 R0");
    }

    @Test
    public void a2_runJob() {
        reworkScrapSyncJobService.runJob(PROJECT);
    }
}
