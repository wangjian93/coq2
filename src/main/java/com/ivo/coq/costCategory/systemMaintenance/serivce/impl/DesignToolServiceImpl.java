package com.ivo.coq.costCategory.systemMaintenance.serivce.impl;

import com.ivo.common.utils.ExcelUtil;
import com.ivo.coq.costCategory.salary.entity.RoleSalary;
import com.ivo.coq.costCategory.systemMaintenance.entity.DesignTool;
import com.ivo.coq.costCategory.systemMaintenance.repository.DesignToolRepository;
import com.ivo.coq.costCategory.systemMaintenance.serivce.DesignToolService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class DesignToolServiceImpl implements DesignToolService {

    @Autowired
    private DesignToolRepository designToolRepository;

    @Override
    public List<DesignTool> getDesignTool() {
        return designToolRepository.findByValidFlagIsTrue();
    }

    @Override
    public void saveDesignTool(List<DesignTool> list) {
        List<DesignTool> oldList = getDesignTool();
        for(DesignTool designTool : oldList) {
            designTool.setValidFlag(false);
            designTool.setExpireDate(new Date());
        }
        for(DesignTool designTool : list) {
            // 维护费用/365*使用天数
            BigDecimal b = new BigDecimal(designTool.getMaintainAmount()/365*designTool.getUseDays());
            designTool.setToolAmount( b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        designToolRepository.saveAll(oldList);
        designToolRepository.saveAll(list);
    }


    @Override
    public Workbook exportExcel() {
        List<DesignTool> list = getDesignTool();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        //首行标题居中、加背景
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER); //水平居中
        cellStyle1.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex()); //设置背景色
        cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND); //设置加粗

        int intRow =0;
        int intCel = 0;
        String[] titleItems = new String[] {"","角色", "工具", "工具使用标准工时/天", "折旧费用/年（RMB)", "维护费用/年（RMB）", "工具费用"};
        Row row1 = sheet.createRow(intRow);
        for(; intCel<titleItems.length; intCel++) {
            Cell cell = row1.createCell(intCel);
            cell.setCellValue(titleItems[intCel]);
            cell.setCellStyle(cellStyle1);
        }
        for(DesignTool designTool : list) {
            intRow++;
            intCel = 0;
            Row row = sheet.createRow(intRow);
            row.createCell(intCel++).setCellValue(designTool.getType());
            row.createCell(intCel++).setCellValue(designTool.getRole());
            row.createCell(intCel++).setCellValue(designTool.getTool());
            row.createCell(intCel++).setCellValue(designTool.getUseDays());
            row.createCell(intCel++).setCellValue(designTool.getDepreciationAmount());
            row.createCell(intCel++).setCellValue(designTool.getMaintainAmount());
            row.createCell(intCel++).setCellValue(designTool.getToolAmount());
            row.createCell(intCel++).setCellValue(designTool.getType());
        }
        return workbook;
    }

    @Override
    public void importExcel(InputStream inputStream, String fileName) throws IOException {
        List<List<Object>> list = ExcelUtil.readExcelFirstSheet(inputStream, fileName);
        int intRow =1;
        try {
            List<DesignTool> designToolList = new ArrayList<>();
            Date effectDate = new Date();
            String ver = Long.toString(effectDate.getTime());
            for(; intRow<list.size(); intRow++) {
                List<Object> dataList = list.get(intRow);
                DesignTool designTool = new DesignTool();
                designTool.setType((String)dataList.get(0));
                designTool.setRole((String)dataList.get(1));
                designTool.setTool((String)dataList.get(2));
                designTool.setUseDays(Double.valueOf(dataList.get(3).toString()));
                designTool.setDepreciationAmount(Double.valueOf(dataList.get(4).toString()));
                designTool.setMaintainAmount(Double.valueOf(dataList.get(5).toString()));
                designTool.setEffectDate(effectDate);
                designTool.setVersion(ver);
                designTool.setValidFlag(true);
                designToolList.add(designTool);
            }
            saveDesignTool(designToolList);
        } catch (Exception e) {
            throw new RuntimeException("解析损耗率Excel第"+intRow+"行错误，数据异常", e);
        }
    }
}
