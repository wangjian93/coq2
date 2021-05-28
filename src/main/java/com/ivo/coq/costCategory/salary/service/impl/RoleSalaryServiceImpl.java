package com.ivo.coq.costCategory.salary.service.impl;

import com.ivo.common.utils.ExcelUtil;
import com.ivo.coq.costCategory.salary.entity.RoleSalary;
import com.ivo.coq.costCategory.salary.repository.RoleSalaryRepository;
import com.ivo.coq.costCategory.salary.service.RoleSalaryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class RoleSalaryServiceImpl implements RoleSalaryService {

    private RoleSalaryRepository roleSalaryRepository;

    @Autowired
    public RoleSalaryServiceImpl(RoleSalaryRepository roleSalaryRepository) {
        this.roleSalaryRepository = roleSalaryRepository;
    }

    @Override
    public List<RoleSalary> getRoleSalary() {
        return roleSalaryRepository.findByValidFlagIsTrue();
    }

    @Override
    public void saveRoleSalary(List<RoleSalary> list) {
        List<RoleSalary> oldList = getRoleSalary();
        for(RoleSalary roleSalary : oldList) {
            roleSalary.setValidFlag(false);
            roleSalary.setExpireDate(new Date());
        }
        roleSalaryRepository.saveAll(oldList);
        roleSalaryRepository.saveAll(list);
    }

    @Override
    public double getRoleSalary(String role) {
        RoleSalary roleSalary = roleSalaryRepository.findByRoleAndValidFlagIsTrue(role);
        return roleSalary == null ? 0 : roleSalary.getSalary();
    }


    @Override
    public Workbook exportExcel() {
        List<RoleSalary> list = getRoleSalary();

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
        String[] titleItems = new String[] {"","角色", "平均本薪"};
        Row row1 = sheet.createRow(intRow);
        for(; intCel<titleItems.length; intCel++) {
            Cell cell = row1.createCell(intCel);
            cell.setCellValue(titleItems[intCel]);
            cell.setCellStyle(cellStyle1);
        }
        for(RoleSalary roleSalary : list) {
            intRow++;
            intCel = 0;
            Row row = sheet.createRow(intRow);
            row.createCell(intCel++).setCellValue(roleSalary.getType());
            row.createCell(intCel++).setCellValue(roleSalary.getRole());
            row.createCell(intCel++).setCellValue(roleSalary.getSalary());

        }
        return workbook;
    }

    @Override
    public void importExcel(InputStream inputStream, String fileName) throws IOException {
        List<List<Object>> list = ExcelUtil.readExcelFirstSheet(inputStream, fileName);
        int intRow =1;
        try {
            List<RoleSalary> roleSalaryList = new ArrayList<>();
            Date effectDate = new Date();
            String ver = Long.toString(effectDate.getTime());
            for(; intRow<list.size(); intRow++) {
                List<Object> dataList = list.get(intRow);
                RoleSalary roleSalary = new RoleSalary();
                roleSalary.setType((String)dataList.get(0));
                roleSalary.setRole((String)dataList.get(1));
                roleSalary.setSalary(Double.valueOf(dataList.get(2).toString()));
                roleSalary.setEffectDate(effectDate);
                roleSalary.setVersion(ver);
                roleSalary.setValidFlag(true);
                roleSalaryList.add(roleSalary);
            }
            saveRoleSalary(roleSalaryList);
        } catch (Exception e) {
            throw new RuntimeException("解析损耗率Excel第"+intRow+"行错误，数据异常", e);
        }
    }


}
