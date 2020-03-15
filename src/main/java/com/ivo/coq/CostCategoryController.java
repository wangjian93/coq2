package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.costCategory.directMaterial.entity.DirectMaterialCost;
import com.ivo.coq.costCategory.directMaterial.service.DirectMaterialCostService;
import com.ivo.coq.costCategory.directMaterial.service.MaterialCostDetailService;
import com.ivo.coq.costCategory.directMaterial.service.OutsourcingThinningCostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq/category")
public class CostCategoryController {

    private DirectMaterialCostService directMaterialCostService;

    private MaterialCostDetailService materialCostDetailService;

    private OutsourcingThinningCostDetailService outsourcingThinningCostDetailService;

    @Autowired
    public CostCategoryController(DirectMaterialCostService directMaterialCostService,
                                  MaterialCostDetailService materialCostDetailService,
                                  OutsourcingThinningCostDetailService outsourcingThinningCostDetailService) {
        this.directMaterialCostService = directMaterialCostService;
        this.materialCostDetailService = materialCostDetailService;
        this.outsourcingThinningCostDetailService = outsourcingThinningCostDetailService;
    }

    /**
     * 获取机种直接材料费用
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/directMaterialCost/{project}")
    public PageResult getDirectMaterialCost(@PathVariable("project") String project) {
        return ResultUtil.successPage(directMaterialCostService.getDirectMaterialCosts(project));
    }

    /**
     * 获取机种直接材料费用-材料费用部分
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/materialCostDetail/{project}")
    public PageResult getMaterialCostDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(materialCostDetailService.getMaterialCostDetails(project));
    }

    /**
     * 获取机种直接材料费用-外包薄化费用部分
     * @param project 机种
     * @return PageResult
     */
    @GetMapping("/outsourcingThinningCostDetail/{project}")
    public PageResult getOutsourcingThinningCostDetail(@PathVariable("project") String project) {
        return ResultUtil.successPage(outsourcingThinningCostDetailService.getOutsourcingThinningCostDetails(project));
    }
}
