package com.ivo.coq;

import com.ivo.common.result.PageResult;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.costCategory.verification.service.VerificationInPlantBasicService;
import com.ivo.coq.costCategory.verification.service.VerificationMachineService;
import com.ivo.coq.costCategory.verification.service.VerificationSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证项目管理
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq")
public class VerificationController {

    private VerificationMachineService verificationMachineService;

    private VerificationSubjectService verificationSubjectService;

    private VerificationInPlantBasicService verificationInPlantBasicService;

    @Autowired
    public VerificationController(VerificationMachineService verificationMachineService,
                                  VerificationSubjectService verificationSubjectService,
                                  VerificationInPlantBasicService verificationInPlantBasicService) {
        this.verificationMachineService = verificationMachineService;
        this.verificationSubjectService = verificationSubjectService;
        this.verificationInPlantBasicService = verificationInPlantBasicService;
    }

    /**
     * 获取获取厂内验证基础数据
     * @return PageResult
     */
    @GetMapping("verificationInPlantBasic")
    public PageResult getVerificationInPlantBasics() {
        int year = 2019;
        return ResultUtil.successPage(verificationInPlantBasicService.getVerificationInPlantBasics(year));
    }

    /**
     * 获取温湿度类实验机台信息
     * @return PageResult
     */
    @GetMapping("humitureVerificationMachine")
    public PageResult getHumitureVerificationMachines() {
        return ResultUtil.successPage(verificationMachineService.getHumitureVerificationMachines());
    }

    /**
     * 获取非温湿度类实验机台信息
     * @return PageResult
     */
    @GetMapping("noHumitureVerificationMachine")
    public PageResult getNoHumitureVerificationMachines() {
        return ResultUtil.successPage(verificationMachineService.getNoHumitureVerificationMachines());
    }

    /**
     * 获取验证项目信息
     * @return PageResult
     */
    @GetMapping("verificationSubjects")
    public PageResult getVerificationSubjects() {
        return ResultUtil.successPage(verificationSubjectService.getVerificationSubjects());
    }
}
