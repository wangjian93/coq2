package com.ivo.test.coq;

import com.ivo.coq.costCategory.salary.entity.BaseSalary;
import com.ivo.coq.costCategory.salary.entity.RdNormalHours;
import com.ivo.coq.costCategory.salary.entity.RoleWorkDays;
import com.ivo.coq.costCategory.salary.repository.BaseSalaryRepository;
import com.ivo.coq.costCategory.salary.repository.RdNormalHoursRepository;
import com.ivo.coq.costCategory.salary.repository.RoleWorkDaysRepository;
import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.repository.ProjectRepository;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据准备
 * @author wj
 * @version 1.0
 */
public class DataPrepare extends AbstractTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BaseSalaryRepository baseSalaryRepository;

    @Autowired
    private RoleWorkDaysRepository roleWorkDaysRepository;

    @Autowired
    private RdNormalHoursRepository rdNormalHoursRepository;

    /**
     * 添加机种
     */
    @Test
    public void addProject() {
        if(projectRepository.findAll().size()>0) return;
        Project project = new Project();
        project.setProject(PROJECT);
        project.setType("IT");
        project.setSize("15.6");
        projectRepository.save(project);
    }

    /**
     * 添加工资费用基础数据
     */
    @Test
    public void addBaseSalary() {
        if(baseSalaryRepository.findAll().size()>0) return;
        BaseSalary baseSalary1 = new BaseSalary();
        baseSalary1.setLabel(BaseSalary.LABEL_BASE);
        baseSalary1.setBaseSalary(6000D);

        BaseSalary baseSalary2 = new BaseSalary();
        baseSalary2.setLabel(BaseSalary.LABEL_RD);
        baseSalary2.setBaseSalary(5500D);

        baseSalaryRepository.save(baseSalary1);
        baseSalaryRepository.save(baseSalary2);
    }

    /**
     * 添加角色工时
     */
    @Test
    public void addRoleWorkDays() {
        String[] role_6 = new String[] {"PM", "PJM", "LCD", "RD"};
        String[] role_3 = new String[] {"EE RD", "ME RD", "RD-Packing", "NPE-Cell", "NPE-Array", "NPE-Lcm"};
        String[] role_5 = new String[] {"LCM TEC"};
        List<RoleWorkDays> list = new ArrayList<>();
        for(String role : role_6) {
            RoleWorkDays roleWorkDays = new RoleWorkDays();
            roleWorkDays.setRole(role);
            roleWorkDays.setWorkDays(6D);
            list.add(roleWorkDays);
        }
        for(String role : role_3) {
            RoleWorkDays roleWorkDays = new RoleWorkDays();
            roleWorkDays.setRole(role);
            roleWorkDays.setWorkDays(3D);
            list.add(roleWorkDays);
        }
        for(String role : role_5) {
            RoleWorkDays roleWorkDays = new RoleWorkDays();
            roleWorkDays.setRole(role);
            roleWorkDays.setWorkDays(5D);
            list.add(roleWorkDays);
        }
        roleWorkDaysRepository.saveAll(list);
    }

    /**
     * 添加RD标准工时
     */
    @Test
    public void addRdNormalHours() {
        String[] role = new String[] {"PM", "RD-Array", "RD-Cell", "RD-EE", "RD-ME", "RD-Packing", "NPE-Array",
        "NPE-Cell", "NPE-LCM", "LCM TEC", "OTM", "DQA"};
        double[] days = new double[] {1, 49, 5, 33, 7, 2.5, 17, 8.5, 14, 14, 1, 10};
        List<RdNormalHours> list = new ArrayList<>();
        for(int i=0; i<12; i++) {
            RdNormalHours rdNormalHours = new RdNormalHours();
            rdNormalHours.setRole(role[i]);
            rdNormalHours.setWorkDays(days[i]);
            list.add(rdNormalHours);
        }
        rdNormalHoursRepository.saveAll(list);
    }
}
