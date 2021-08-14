package com.ivo.product.service.impl;

import com.ivo.product.entity.RmaMonth;
import com.ivo.product.entity.WhScrap;
import com.ivo.product.entity.WhScrapMonth;
import com.ivo.product.repository.RmaMonthRepository;
import com.ivo.product.repository.WhScrapMonthRepository;
import com.ivo.product.repository.WhScrapRepository;
import com.ivo.product.service.WhScrapService;
import com.ivo.rest.eifdb.mapper.EifMapper;
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
public class WhScrapServiceImpl implements WhScrapService {

    @Resource
    private EifMapper eifMapper;

    @Resource
    private WhScrapRepository whScrapRepository;

    @Resource
    private WhScrapMonthRepository whScrapMonthRepository;

    @Resource
    private RmaMonthRepository rmaMonthRepository;

    @Override
    public void syncWhScrap() {
        whScrapRepository.deleteAll();
        List<Map> list = eifMapper.getWhScrap();
        List<WhScrap> whScrapList = new ArrayList<>();
        for(Map map : list) {
            WhScrap whScrap = new WhScrap();
            whScrap.setDateOfOrder((Date) map.get("dateOfOrder"));
            whScrap.setTrackingNumber((String) map.get("trackingNumber"));
            whScrap.setCostCenter((String) map.get("costCenter"));
            whScrap.setUserDepartment((String) map.get("userDepartment"));
            whScrap.setScrapMoneyCny((double) map.get("scrapMoneyCny"));
            whScrap.setProject((String) map.get("project"));
            whScrap.setExpType((String) map.get("expType"));
            whScrap.setCostType((String) map.get("costType"));
            whScrapList.add(whScrap);
        }
        whScrapRepository.saveAll(whScrapList);
    }

    @Override
    public void computeWhScrapMonth(String month) {
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

        //By月IVO责报废损失(外包)=仓库报废单金额之和（申请人为OMD，报废成本中心为B2100（阵列厂））
        //IVO责报废损失(外包)=仓库报废单金额之和（申请人为OMD，报废成本中心为B2200（面板厂））
        //M1责报废损失(外包)=仓库报废单金额之和（申请人为OMD，报废成本中心为M0002（M1厂））
        //M2责报废损失(外包)=仓库报废单金额之和（申请人为OMD，报废成本中心为B3002（M2厂））
        String[] userDepartments = {"10000684","10000578"};   //外包服务部
        String costCenter_LCM1="M0002";
        String costCenter_LCM2="B3002";
        String costCenter_CELL="B2200";
        String costCenter_ARRAY="B2100";
        Double total_lcm1 = whScrapRepository.sumByCostCenterAndUserDepartment(costCenter_LCM1, userDepartments, fromDate, toDate);
        Double total_lcm2 = whScrapRepository.sumByCostCenterAndUserDepartment(costCenter_LCM2, userDepartments, fromDate, toDate);
        Double total_cell = whScrapRepository.sumByCostCenterAndUserDepartment(costCenter_CELL, userDepartments, fromDate, toDate);
        Double total_array = whScrapRepository.sumByCostCenterAndUserDepartment(costCenter_ARRAY, userDepartments, fromDate, toDate);
        if(total_lcm1 == null) total_lcm1=0D;
        if(total_lcm2 == null) total_lcm2=0D;
        if(total_cell == null) total_cell=0D;
        if(total_array == null) total_array=0D;

        WhScrapMonth lcm1 = new WhScrapMonth();
        lcm1.setMonth(month);
        lcm1.setFab("LCM1");
        lcm1.setAmount(total_lcm1);

        WhScrapMonth lcm2 = new WhScrapMonth();
        lcm2.setMonth(month);
        lcm2.setFab("LCM2");
        lcm2.setAmount(total_lcm2);

        WhScrapMonth cell = new WhScrapMonth();
        cell.setMonth(month);
        cell.setFab("CELL");
        cell.setAmount(total_cell);

        WhScrapMonth array = new WhScrapMonth();
        array.setMonth(month);
        array.setFab("ARRAY");
        array.setAmount(total_array);

        whScrapMonthRepository.save(lcm1);
        whScrapMonthRepository.save(lcm2);
        whScrapMonthRepository.save(cell);
        whScrapMonthRepository.save(array);
    }


    @Override
    public void computeRmaMonth(String month) {
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

        //Cell RMA=报废成本中心为BX133 & 报废原因为[0300]RMA品不可维修在库品报废 & 明细Material No内开头为14的金额之和+B-trip金额
        //LCM1 RMA=实验类别为【05 RMA】、【06 IVE成品】下报废成本中心为M0024的所有报废金额之和
        //LCM2 RMA=报废成本中心为BX133 & 报废原因为[0200]RMA品工单报废金额之和+报废成本中心为BX133 & 报废原因为[0300]RMA品不可维修在库品报废 & 明细Material No内开头除14的金额之和
        String costCenter_cell = "BX133";
        String costCenter_Lcm1 = "M0024";
        String costCenter_Lcm2 = "BX133";
        String[] costType_cell = {"0300"};
        String[] expType_lcm1 = {"05","06"};
        String[] costType_lcm2 = {"0200","0300"};

        Double total_cell = whScrapRepository.sumByCostCenterAndCostType(costCenter_cell, costType_cell, fromDate, toDate);
        Double total_lcm2 = whScrapRepository.sumByCostCenterAndCostType(costCenter_Lcm2, costType_lcm2, fromDate, toDate);
        Double total_lcm1 = whScrapRepository.sumByCostCenterAndExpType(costCenter_Lcm1, expType_lcm1, fromDate, toDate);
        if(total_cell == null) total_cell = 0d;
        if(total_lcm2 == null) total_lcm2 = 0d;
        if(total_lcm1 == null) total_lcm1 = 0d;

        RmaMonth lcm1 = new RmaMonth();
        lcm1.setFab("LCM1");
        lcm1.setMonth(month);
        lcm1.setAmount(total_lcm1);

        RmaMonth lcm2 = new RmaMonth();
        lcm2.setFab("LCM2");
        lcm2.setMonth(month);
        lcm2.setAmount(total_lcm2);

        RmaMonth cell = new RmaMonth();
        cell.setFab("CELL");
        cell.setMonth(month);
        cell.setAmount(total_cell);
        rmaMonthRepository.save(lcm1);
        rmaMonthRepository.save(lcm2);
        rmaMonthRepository.save(cell);
    }
}
