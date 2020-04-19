package com.ivo.config.dataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 第三数据源
 * @author wj
 * @version 1.0
 */
@Configuration
@Slf4j
public class FourthDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.fourth")
    public DataSource fourthDataSource() {
        log.info("创建第四数据源...");
        return DruidDataSourceBuilder.create().build();
    }
}
