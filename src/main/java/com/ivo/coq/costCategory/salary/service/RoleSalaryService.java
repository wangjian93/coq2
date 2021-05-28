package com.ivo.coq.costCategory.salary.service;

import com.ivo.coq.costCategory.salary.entity.RoleSalary;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface RoleSalaryService {

    List<RoleSalary> getRoleSalary();

    void saveRoleSalary(List<RoleSalary> list);

    double getRoleSalary(String role);

    Workbook exportExcel();
    void importExcel(InputStream inputStream, String fileName) throws IOException;
}
