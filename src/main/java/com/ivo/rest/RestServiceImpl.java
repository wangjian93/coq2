package com.ivo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class RestServiceImpl implements RestService {

    private static final String URL_Arcadia = "https://myivo.ivo.com.cn/Arcadia/getBEItems.do";

    private static RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Map<String, String>> getTravelDetailsFromBe(Date fromDate, Date toDate, String employee) {
        if(fromDate == null || toDate == null || StringUtils.isEmpty(employee)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        log.info("请求出差报支单接口获取人员的差旅费信息" + employee + " " + sdf.format(fromDate) + "至" + sdf.format(toDate));
        String from = sdf.format(fromDate);
        String to = sdf.format(toDate);
        String returnStr = restTemplate.getForObject(URL_Arcadia
                + "?employeeId=" + employee
                + "&fromDate=" + from
                + "&toDate=" + to, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List list = new ArrayList();
        try {
            list = objectMapper.readValue(returnStr, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(">>差旅费共: " + list.size());
        return list;
    }

}
