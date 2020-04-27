package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.station.service.StationCostShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机种经过的站点单片费用
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq")
public class StationController {

    private StationCostShareService stationCostShareService;

    @Autowired
    public StationController(StationCostShareService stationCostShareService) {
        this.stationCostShareService = stationCostShareService;
    }

    /**
     * 获取机种的经过所有站点的单片费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/station/{project}")
    public PageResult list(@PathVariable("project") String project) {
        return ResultUtil.successPage(stationCostShareService.getStationCostShare(project));
    }
}
