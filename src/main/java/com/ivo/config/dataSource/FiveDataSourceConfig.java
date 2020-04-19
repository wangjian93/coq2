package com.ivo.config.dataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author wj
 * @version 1.0
 */
@Configuration
@Slf4j
public class FiveDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.five")
    public DataSource fiveDataSource() {
        log.info("创建第五数据源...");
        return DruidDataSourceBuilder.create().build();
    }
}
