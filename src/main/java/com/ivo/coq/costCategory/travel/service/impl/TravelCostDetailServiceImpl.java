package com.ivo.coq.costCategory.travel.service.impl;

import com.ivo.common.enums.StageEnum;
import com.ivo.coq.costCategory.travel.entity.TravelCostDetail;
import com.ivo.coq.costCategory.travel.repository.TravelCostDetailRepository;
import com.ivo.coq.costCategory.travel.service.TravelCostDetailService;
import com.ivo.coq.project.entity.Member;
import com.ivo.coq.project.service.MemberService;
import com.ivo.coq.project.service.MilestoneService;
import com.ivo.rest.RestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class TravelCostDetailServiceImpl implements TravelCostDetailService {

    private TravelCostDetailRepository repository;

    private RestService restService;

    private MemberService memberService;

    private MilestoneService milestoneService;

    @Autowired
    public TravelCostDetailServiceImpl(TravelCostDetailRepository repository, RestService restService,
                                       MemberService memberService, MilestoneService milestoneService) {
        this.repository = repository;
        this.restService = restService;
        this.memberService = memberService;
        this.milestoneService = milestoneService;
    }

    @Override
    public List<TravelCostDetail> getTravelCostDetail(String project) {
        return repository.findByProject(project);
    }

    @Override
    public List<TravelCostDetail> getTravelCostDetail(String project, String type) {
        return repository.findByProjectAndCostType(project, type);
    }

    @Override
    public void syncTravelCostDetail(String project) {
        log.info("从出差报支单同步差旅信息 " + project);
        repository.deleteAll(getTravelCostDetail(project));
        List<Member> memberList = memberService.getMembers(project);
        // 人员去重
        Set<String> members = new HashSet<>();
        for(Member member : memberList) {
            members.add(member.getEmployeeId());
        }

        // KICK OFF ~ NPRB 期间属于预防
        Date[] nprb_date = milestoneService.getStageAndEndDate(project, StageEnum.NPRB.getStage());
        // NPRB ~ DESIGN 期间属于内损
        Date[] design_date = milestoneService.getStageAndEndDate(project, StageEnum.Design.getStage());
        List<TravelCostDetail> travelCostDetailList = new ArrayList<>();
        for(String member : members) {
            List<Map<String, String>> list = restService.getTravelDetailsFromBe(nprb_date[0], nprb_date[1], member);
            List<Map<String, String>> list_ = restService.getTravelDetailsFromBe(design_date[0], design_date[1], member);
            if(list != null) {
                for(Map<String, String> map : list) {
                    TravelCostDetail travelCostDetail = new TravelCostDetail(project, TravelCostDetail.TYPE_preventionCost);
                    mapConvertTravelCostDetail(map, travelCostDetail);
                    travelCostDetailList.add(travelCostDetail);
                }
            }
            if(list_ != null) {
                for(Map<String, String> map : list_) {
                    TravelCostDetail travelCostDetail = new TravelCostDetail(project, TravelCostDetail.TYPE_inLossCost);
                    mapConvertTravelCostDetail(map, travelCostDetail);
                    travelCostDetailList.add(travelCostDetail);
                }
            }
        }
        repository.saveAll(travelCostDetailList);
    }

    /**
     * 将map值复制发哦CostDetail对象中
     */
    private void mapConvertTravelCostDetail(Map<String, String> map, TravelCostDetail travelCostDetail) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        travelCostDetail.setEmployee(map.get("Payee_FK"));
        travelCostDetail.setMiscAmount(Double.valueOf(map.get("MiscAmount")));
        travelCostDetail.setTelphoneAmount(Double.valueOf(map.get("TelphoneAmount")));
        travelCostDetail.setTravelNumber(map.get("TrackingNumber"));
        travelCostDetail.setIvomiscAmount(Double.valueOf(map.get("ivomiscamount")));
        travelCostDetail.setAccommodationAmount(Double.valueOf(map.get("AccommodationAmount")));
        try {
            travelCostDetail.setTravelDate(sdf.parse(map.get("DateOfTrip")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        travelCostDetail.setEntertainmentAmount(Double.valueOf(map.get("EntertainmentAmount")));
        travelCostDetail.setTransportationAmount(Double.valueOf(map.get("TransportationAmount")));
    }
}
