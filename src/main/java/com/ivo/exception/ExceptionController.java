package com.ivo.exception;

import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.project.entity.Member;
import com.ivo.coq.project.entity.Milestone;
import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.MemberService;
import com.ivo.coq.project.service.MilestoneService;
import com.ivo.coq.project.service.ProjectService;
import com.ivo.coq.project.service.SampleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@RestController
public class ExceptionController {

    @Autowired
    ProjectService projectService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private MemberService memberService;

    @RequestMapping("/coqException")
    public Result coqException() throws ParseException {
        String[] msgs = new String[] {
                "PLM数据未维护", "Time不符合", "实验申请单不符合"
        };
        class E{
            private String project;
            private String stage;
            private String pm;
            private String msg;
            private String ds;
            private Date endDate;
            public E(String project, String stage, String pm, String msg, String ds, Date endDate) {
                this.project = project;
                this.stage = stage;
                this.pm = pm;
                this.msg = msg;
                this.ds = ds;
                this.endDate =endDate;
            }

            public String getProject() {
                return this.project;
            }
            public String getStage() {
                return this.stage;
            }
            public String getPm() {
                return this.pm;
            }
            public String getMsg() {
                return this.msg;
            }
            public String getDs() {
                return this.ds;
            }
            public Date getEndDate() {
                return this.endDate;
            }

        }

        List<E> list = new ArrayList<>();
        List<Project> projectList = projectService.getProjects();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Project p : projectList) {
            String project = p.getProject();
            List<Milestone> milestoneList = milestoneService.getMilestone(project);
            Date date = sdf.parse("2020-01-01");
            Date endDate = null;
            for(Milestone milestone : milestoneList) {
                if(milestone.getMilestone().equals("NPRB")) {
                    endDate = milestone.getEndDate();
                }
            }
            if(endDate != null && endDate.before(date)) continue;

            List<Sample> sampleList = sampleService.getSamples(project);
            List<Member> memberList = memberService.getMembers(project, "PJM");
            String member = "";
            if(memberList.size()>0) {
                member = memberList.get(0).getEmployeeId() + "/" + memberList.get(0).getEmployeeName();
            }

            List<String> stageList = new ArrayList<>();
            for(Sample sample : sampleList) {
                Integer time = sample.getTime();
                String orderNumber = sample.getOrderNumber();
                String stage = sample.getStage();
                if(time == null || time<1 || time >99) {
                    list.add(new E(project, stage, member, msgs[1], "Time="+time, endDate));
                }
                if(!StringUtils.startsWithAny(orderNumber, "ED", "EE", "OEE")) {
                    list.add(new E(project, stage, member, msgs[2], "EE/ED="+orderNumber, endDate));
                }
                stageList.add(stage);
            }

            for(Milestone milestone : milestoneList) {
                String m = milestone.getMilestone();
                if(!StringUtils.containsAny(m, "NPRB", "Design", "EVT", "DVT", "MP") ) continue;
                if(StringUtils.containsIgnoreCase(m, "Design")) m = "Design";
                if(stageList.contains(m)) continue;
                list.add(new E(project, m, member, msgs[0], "", endDate));
            }
        }

        return ResultUtil.successPage(list);
    }
}
