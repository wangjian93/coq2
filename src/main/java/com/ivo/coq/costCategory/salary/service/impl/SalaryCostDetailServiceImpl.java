package com.ivo.coq.costCategory.salary.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.salary.entity.BaseSalary;
import com.ivo.coq.costCategory.salary.entity.RoleWorkDays;
import com.ivo.coq.costCategory.salary.entity.SalaryCostDetail;
import com.ivo.coq.costCategory.salary.repository.BaseSalaryRepository;
import com.ivo.coq.costCategory.salary.repository.RoleWorkDaysRepository;
import com.ivo.coq.costCategory.salary.repository.SalaryCostDetailRepository;
import com.ivo.coq.costCategory.salary.service.SalaryCostDetailService;
import com.ivo.coq.project.entity.Member;
import com.ivo.coq.project.service.MemberService;
import com.ivo.coq.project.service.MilestoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class SalaryCostDetailServiceImpl implements SalaryCostDetailService {

    private SalaryCostDetailRepository repository;

    private RoleWorkDaysRepository roleWorkDaysRepository;

    private MemberService memberService;

    private BaseSalaryRepository baseSalaryRepository;

    private MilestoneService milestoneService;

    @Autowired
    public SalaryCostDetailServiceImpl(SalaryCostDetailRepository repository, RoleWorkDaysRepository roleWorkDaysRepository,
                                       MemberService memberService, BaseSalaryRepository baseSalaryRepository,
                                       MilestoneService milestoneService) {
        this.repository = repository;
        this.roleWorkDaysRepository = roleWorkDaysRepository;
        this.memberService = memberService;
        this.baseSalaryRepository = baseSalaryRepository;
        this.milestoneService = milestoneService;
    }

    @Override
    public List<SalaryCostDetail> getSalaryCostDetail(String project) {
        return repository.findByProject(project);
    }

    @Override
    public void syncSalaryCostDetail(String project) {
        log.info("同步计算人员薪资费用 " + project);
        // 基本薪资
        Double baseSalary = baseSalaryRepository.findFirstByLabel(BaseSalary.LABEL_BASE).getBaseSalary();
        // Delay天数
        Double delayDays = (double) milestoneService.getDelayDays(project, StageEnum.NPRB.getStage());
        repository.deleteAll(getSalaryCostDetail(project));
        List<RoleWorkDays> roleWorkDaysList = roleWorkDaysRepository.findAll();
        List<SalaryCostDetail> salaryCostDetailList = new ArrayList<>();
        for(RoleWorkDays roleWorkDays : roleWorkDaysList) {
            List<Member> memberList = memberService.getMembers(project, roleWorkDays.getRole());
            if(memberList.size() == 0) continue;
            SalaryCostDetail salaryCostDetail = new SalaryCostDetail(project);
            salaryCostDetail.setPersonNumber(memberList.size());
            salaryCostDetail.setRole(roleWorkDays.getRole());
            salaryCostDetail.setWorkDays(roleWorkDays.getWorkDays());
            salaryCostDetail.setDelayDays(delayDays);

            // 预防部分:（基本薪资/21.75）* 工作日 * 人数
            Double d = DoubleUtil.divide(baseSalary, 21.75);
            Double preventionAmount = DoubleUtil.multiply(d,
                    salaryCostDetail.getWorkDays(), (double) salaryCostDetail.getPersonNumber());
            salaryCostDetail.setPreventionAmount(preventionAmount);
            // 内损部分：基本薪资/21.75）* delay天数 * 人数
            Double inLossAmount = DoubleUtil.multiply(d,
                    salaryCostDetail.getDelayDays(), (double) salaryCostDetail.getPersonNumber());
            salaryCostDetail.setInLossAmount(inLossAmount);
            salaryCostDetailList.add(salaryCostDetail);
        }
        repository.saveAll(salaryCostDetailList);
    }
}
