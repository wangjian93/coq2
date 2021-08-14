package com.ivo.coq.costCategory.salary.service.impl;

import com.ivo.common.utils.ExcelUtil;
import com.ivo.coq.costCategory.salary.entity.DesignWorkDay;
import com.ivo.coq.costCategory.salary.entity.RoleSalary;
import com.ivo.coq.costCategory.salary.entity.RoleWorkDay;
import com.ivo.coq.costCategory.salary.repository.DesignWorkDayRepository;
import com.ivo.coq.costCategory.salary.service.DesignWorkDayService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class DesignWorkDayServiceImpl implements DesignWorkDayService {

    @Autowired
    private DesignWorkDayRepository designWorkDayRepository;

    @Override
    public List<DesignWorkDay> getRoleWorkDays() {
        return designWorkDayRepository.findByValidFlagIsTrue();
    }

    @Override
    public void saveRoleWorkDays(List<DesignWorkDay> list) {
        List<DesignWorkDay> oldList = getRoleWorkDays();
        for(DesignWorkDay designWorkDay : oldList) {
            designWorkDay.setValidFlag(false);
            designWorkDay.setExpireDate(new Date());
        }
        designWorkDayRepository.saveAll(oldList);
        designWorkDayRepository.saveAll(list);
    }

    @Override
    public double getRoleWorkDays(String role) {
        DesignWorkDay designWorkDay = designWorkDayRepository.findByRoleAndValidFlagIsTrue(role);
        return designWorkDay.getWorkDays();
    }

    @Override
    public Workbook exportExcel() {
        List<DesignWorkDay> list = getRoleWorkDays();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        //首行标题居中、加背景
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER); //水平居中
        cellStyle1.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex()); //设置背景色
        cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND); //设置加粗
        //设置列宽
        sheet.setColumnWidth(0, 20*256);
        sheet.setColumnWidth(1, 50*256);
        sheet.setColumnWidth(2, 20*256);

        int intRow =0;
        int intCel = 0;
        String[] titleItems = new String[] {"","角色", "标准工时/天"};
        Row row1 = sheet.createRow(intRow);
        for(; intCel<titleItems.length; intCel++) {
            Cell cell = row1.createCell(intCel);
            cell.setCellValue(titleItems[intCel]);
            cell.setCellStyle(cellStyle1);
        }
        for(DesignWorkDay designWorkDay : list) {
            intRow++;
            intCel = 0;
            Row row = sheet.createRow(intRow);
            row.createCell(intCel++).setCellValue(designWorkDay.getType());
            row.createCell(intCel++).setCellValue(designWorkDay.getRole());
            row.createCell(intCel++).setCellValue(designWorkDay.getWorkDays());
        }
        return workbook;
    }

    @Override
    public void importExcel(InputStream inputStream, String fileName) throws IOException {
        List<List<Object>> list = ExcelUtil.readExcelFirstSheet(inputStream, fileName);
        int intRow =1;
        try {
            List<DesignWorkDay> designWorkDayList = new ArrayList<>();
            Date effectDate = new Date();
            String ver = Long.toString(effectDate.getTime());
            for(; intRow<list.size(); intRow++) {
                List<Object> dataList = list.get(intRow);
                DesignWorkDay designWorkDay = new DesignWorkDay();
                designWorkDay.setType((String)dataList.get(0));
                designWorkDay.setRole((String)dataList.get(1));
                designWorkDay.setWorkDays(Double.valueOf(dataList.get(2).toString()));
                designWorkDay.setEffectDate(effectDate);
                designWorkDay.setVersion(ver);
                designWorkDay.setValidFlag(true);
                designWorkDayList.add(designWorkDay);

                
            }
            saveRoleWorkDays(designWorkDayList);
        } catch (Exception e) {
            throw new RuntimeException("解析损耗率Excel第"+intRow+"行错误，数据异常", e);
        }
    }
}
