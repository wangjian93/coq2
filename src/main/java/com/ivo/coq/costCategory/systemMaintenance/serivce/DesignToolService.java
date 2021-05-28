package com.ivo.coq.costCategory.systemMaintenance.serivce;

import com.ivo.coq.costCategory.systemMaintenance.entity.DesignTool;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface DesignToolService {

    List<DesignTool> getDesignTool();

    void saveDesignTool(List<DesignTool> list);

    Workbook exportExcel();
    void importExcel(InputStream inputStream, String fileName) throws IOException;
}
