package com.ivo.product.service.impl;

import com.ivo.product.entity.Oba;
import com.ivo.product.entity.ObaMonth;
import com.ivo.product.entity.RmaMonth;
import com.ivo.product.repository.ObaMonthRepository;
import com.ivo.product.repository.ObaRepository;
import com.ivo.product.service.ObaService;
import com.ivo.rest.eifdb.mapper.EifMapper;
import com.ivo.rest.qms.mapper.QmsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class ObaServiceImpl implements ObaService {

    @Resource
    private QmsMapper qmsMapper;

    @Resource
    private ObaRepository obaRepository;

    @Resource
    private EifMapper eifMapper;

    @Resource
    private ObaMonthRepository obaMonthRepository;



    @Override
    public void syncOba() {
        obaRepository.deleteAll();
        List<Map> mapList = qmsMapper.getOba();
        List<Oba> obaList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map map : mapList) {
            Oba oba = new Oba();
            oba.setProject((String) map.get("project"));
            oba.setPeNumber((String) map.get("peNumber"));
            try {
                oba.setHappenTime(sdf.parse((String) map.get("happenTime")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            oba.setObaType((String) map.get("obaType"));
            try {
                oba.setQuantity(Double.valueOf(((String) map.get("quantity")).replace(" ", "").replace(",","")));
            } catch (NumberFormatException e) {
                oba.setQuantity(0);
            }
            try {
                oba.setPrice(Double.valueOf((String) map.get("price")));
            } catch (NumberFormatException e) {
                oba.setPrice(0);
            }
            try {
                oba.setAmount(
                        Double.valueOf(((String) map.get("amount")).replace(" ", "").replace(",",""))
                );
            } catch (NumberFormatException e) {
                oba.setAmount(0);
            }

            oba.setCreateUser((String) map.get("create_user"));
            String productType = (String) map.get("productType");
            oba.setProductType(productType);
            String fab;
            if(StringUtils.equals(productType, "Module")) fab = "LCM";
            else fab = "CELL";
            oba.setFab(fab);
            obaList.add(oba);
        }
        obaRepository.saveAll(obaList);
    }

    @Override
    public void syncFab() {
//        List<Oba> obaList = obaRepository.findAll();
//        //客户服务一部员工负责的为Lcm,客户服务二部为CELL
//        String cell_deptId = "10000931"; //客户服务二部
//        String lcm_deptId = "10000074"; //客户服务一部
//        List<String> cell_employeeList = eifMapper.getEmployeesByDeptId("%"+cell_deptId+"%");
//        List<String> lcm_employeeList = eifMapper.getEmployeesByDeptId("%"+lcm_deptId+"%");
//        for(Oba oba : obaList) {
//            String createUser = oba.getCreateUser();
//            if(cell_employeeList.contains(createUser)) {
//                oba.setFab("CELL");
//            } else if (lcm_employeeList.contains(createUser)) {
//                oba.setFab("LCM");
//            }
//        }
//        obaRepository.saveAll(obaList);
    }

    @Override
    public void computeObaMonth(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date fromDate;
        Date toDate;
        try {
            fromDate = sdf.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(fromDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        toDate = ca.getTime();

        Double cell_obaAmount = obaRepository.sumObaAmont(fromDate, toDate, "CELL");
        Double lcm_obaAmount = obaRepository.sumObaAmont(fromDate, toDate, "LCM");
        if(cell_obaAmount == null) cell_obaAmount = 0d;
        if(lcm_obaAmount == null) lcm_obaAmount = 0d;


        ObaMonth cell = new ObaMonth();
        cell.setFab("CELL");
        cell.setMonth(month);
        cell.setAmount(cell_obaAmount);

        ObaMonth lcm = new ObaMonth();
        lcm.setFab("LCM");
        lcm.setMonth(month);
        lcm.setAmount(lcm_obaAmount);

        obaMonthRepository.save(cell);
        obaMonthRepository.save(lcm);
    }
}
