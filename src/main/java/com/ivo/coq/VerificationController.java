package com.ivo.coq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ivo.common.result.PageResult;
import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.costCategory.salary.entity.RoleSalary;
import com.ivo.coq.costCategory.verification.entity.VerificationInPlantBasic;
import com.ivo.coq.costCategory.verification.service.VerificationInPlantBasicService;
import com.ivo.coq.costCategory.verification.service.VerificationMachineService;
import com.ivo.coq.costCategory.verification.service.VerificationSubjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<VerificationInPlantBasic> list = verificationInPlantBasicService.getVerificationInPlantBasics();
        List<VerificationInPlantBasic> list2 = new ArrayList<>();
        for(VerificationInPlantBasic basic : list) {
            if(StringUtils.equalsAnyIgnoreCase(basic.getName(),
                    VerificationInPlantBasic.MONTHLY_SALARY,
                    VerificationInPlantBasic.ELECTRICITY_BILL_PRICE,
                    VerificationInPlantBasic.MAINTAIN_COST,
                    VerificationInPlantBasic.HUMITURE_COST_PER,
                    VerificationInPlantBasic.MAINTAIN_COST_Per,
                    VerificationInPlantBasic.MAN_POWE_Cost_Per
                    ))
                continue;
            list2.add(basic);
        }
        return ResultUtil.successPage(list2);
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
     * 获取实验机台信息
     * @return PageResult
     */
    @GetMapping("verificationMachine")
    public PageResult getVerificationMachines() {
        return ResultUtil.successPage(verificationMachineService.getVerificationMachines());
    }

    /**
     * 获取验证项目信息
     * @return PageResult
     */
    @GetMapping("verificationSubjects")
    public PageResult getVerificationSubjects() {
        return ResultUtil.successPage(verificationSubjectService.getVerificationSubjects());
    }


    @PostMapping("/verificationInPlantBasic/save")
    public Result saveRoleSalary(@RequestParam(name = "data") String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<VerificationInPlantBasic> list;
        try {
            list = objectMapper.readValue(jsonData, typeFactory.constructCollectionType(List.class, VerificationInPlantBasic.class));
            Date effectDate = new Date();
            String ver = Long.toString(effectDate.getTime());
            for(VerificationInPlantBasic basic : list) {
                basic.setEffectDate(effectDate);
                basic.setVersion(ver);
                basic.setValidFlag(true);
            }
            verificationInPlantBasicService.save(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }
}
