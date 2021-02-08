package com.ivo.coq;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wj
 * @version 1.0
 */
@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(String url) {
        if(StringUtils.equals(url, "home2")) {
            return "page/Home2.html";
        } else {
            return "page/Home3.html";
        }
    }
}
