package com.ivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 质量成本系统应用
 * @author wj
 * @version 1.0
 */
@SpringBootApplication
public class CoqApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CoqApplication.class, args);
    }
}
