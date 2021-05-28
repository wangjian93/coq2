package com.ivo.coq.costCategory.salary.service;

import com.ivo.coq.costCategory.salary.entity.DesignWorkDay;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface DesignWorkDayService {

    List<DesignWorkDay> getRoleWorkDays();

    void saveRoleWorkDays(List<DesignWorkDay> list);

    double getRoleWorkDays(String role);

    Workbook exportExcel();
    void importExcel(InputStream inputStream, String fileName) throws IOException;
}
