package com.ivo.coq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ivo.common.result.PageResult;
import com.ivo.common.result.Result;
import com.ivo.common.utils.ResultUtil;
import com.ivo.coq.costCategory.salary.entity.RoleSalary;
import com.ivo.coq.costCategory.salary.service.RoleSalaryService;
import com.ivo.core.decryption.DecryptException;
import com.ivo.core.decryption.IVODecryptionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@RestController
@RequestMapping("/coq/roleSalary")
public class RoleSalaryController {

    @Autowired
    private RoleSalaryService roleSalaryService;

    @GetMapping("/list")
    public PageResult getRoleSalary() {
        return ResultUtil.successPage(roleSalaryService.getRoleSalary());
    }

    @PostMapping("/save")
    public Result saveRoleSalary(@RequestParam(name = "data") String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<RoleSalary> list;
        try {
            list = objectMapper.readValue(jsonData, typeFactory.constructCollectionType(List.class, RoleSalary.class));
            Date effectDate = new Date();
            String ver = Long.toString(effectDate.getTime());
            for(RoleSalary roleSalary : list) {
                roleSalary.setEffectDate(effectDate);
                roleSalary.setVersion(ver);
            }
            roleSalaryService.saveRoleSalary(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }


    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        Workbook workbook = roleSalaryService.exportExcel();
        response.setContentType("application/vnd.ms-excel;chartset=utf-8");
        String fileName = "平均薪资";
        fileName = URLEncoder.encode(fileName, "UTF8");
        response.setHeader("Content-Disposition", "attachment;filename="+fileName + ".xlsx");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }

    @PostMapping(value = "/importExcel", headers = "content-type=multipart/form-data")
    public Result importExcel(@RequestParam("file") MultipartFile file) {
        try {
            //IVO文件解密
            byte[] bytes = IVODecryptionUtils.decrypt(file.getInputStream());
            InputStream inputStream = new ByteArrayInputStream(bytes);
            String fileName = file.getOriginalFilename();
            roleSalaryService.importExcel(inputStream, fileName);
        } catch (IOException e) {
            return ResultUtil.error("Excel导入失败，文件读取异常");
        } catch (DecryptException e) {
            return ResultUtil.error("Excel导入失败，文件解密异常");
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.success("Excel导入成功");
    }


}
