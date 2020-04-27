package com.ivo.coq.costCategory.salary.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.coq.costCategory.salary.entity.BaseSalary;
import com.ivo.coq.costCategory.salary.entity.RdNormalHours;
import com.ivo.coq.costCategory.salary.entity.SalaryCostNormalHoursDetail;
import com.ivo.coq.costCategory.salary.repository.BaseSalaryRepository;
import com.ivo.coq.costCategory.salary.repository.RdNormalHoursRepository;
import com.ivo.coq.costCategory.salary.repository.SalaryCostNormalHoursDetailRepository;
import com.ivo.coq.costCategory.salary.service.SalaryCostNormalHoursDetailService;
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
public class SalaryCostNormalHoursDetailServiceImpl implements SalaryCostNormalHoursDetailService {

    private SalaryCostNormalHoursDetailRepository repository;

    private RdNormalHoursRepository rdNormalHoursRepository;

    private BaseSalaryRepository baseSalaryRepository;

    private MilestoneService milestoneService;

    private MemberService memberService;

    @Autowired
    public SalaryCostNormalHoursDetailServiceImpl(SalaryCostNormalHoursDetailRepository repository,
                                                  RdNormalHoursRepository rdNormalHoursRepository,
                                                  BaseSalaryRepository baseSalaryRepository,
                                                  MilestoneService milestoneService,
                                                  MemberService memberService) {
        this.repository = repository;
        this.rdNormalHoursRepository = rdNormalHoursRepository;
        this.baseSalaryRepository = baseSalaryRepository;
        this.milestoneService = milestoneService;
        this.memberService = memberService;
    }

    @Override
    public List<SalaryCostNormalHoursDetail> getSalaryCostNormalHoursDetail(String project) {
        return repository.findByProject(project);
    }

    @Override
    public void syncSalaryCostNormalHoursDetail(String project) {
        log.info("同步计算RD标准工时费用 " + project);
        repository.deleteAll(getSalaryCostNormalHoursDetail(project));
        // 底薪
        Double baseSalary = baseSalaryRepository.findFirstByLabel(BaseSalary.LABEL_RD).getBaseSalary();
        // Delay天数
        Double delayDays = (double) milestoneService.getDelayDays(project, StageEnum.Design.getStage());
        List<RdNormalHours> rdNormalHoursList = rdNormalHoursRepository.findAll();
        List<SalaryCostNormalHoursDetail> salaryCostNormalHoursDetails = new ArrayList<>();
        for(RdNormalHours rdNormalHours : rdNormalHoursList) {
            List<Member> memberList = memberService.getMembers(project, rdNormalHours.getRole());
            if(memberList.size() == 0) continue;
            SalaryCostNormalHoursDetail salaryCostNormalHoursDetail = new SalaryCostNormalHoursDetail(project);
            salaryCostNormalHoursDetail.setRole(rdNormalHours.getRole());
            salaryCostNormalHoursDetail.setWorkDays(rdNormalHours.getWorkDays());
            salaryCostNormalHoursDetail.setDelayDays(delayDays);

            // 预防： （底薪/21.75）* 工作日
            Double d = DoubleUtil.divide(baseSalary, 21.75);
            Double preventionAmount = DoubleUtil.multiply(d, salaryCostNormalHoursDetail.getWorkDays());
            salaryCostNormalHoursDetail.setPreventionAmount(preventionAmount);
            // 内损： （底薪/21.75）* delay天数
            Double inLossAmount = DoubleUtil.multiply(d, salaryCostNormalHoursDetail.getDelayDays());
            salaryCostNormalHoursDetail.setInLossAmount(inLossAmount);

            salaryCostNormalHoursDetails.add(salaryCostNormalHoursDetail);
        }
        repository.saveAll(salaryCostNormalHoursDetails);
    }
}
