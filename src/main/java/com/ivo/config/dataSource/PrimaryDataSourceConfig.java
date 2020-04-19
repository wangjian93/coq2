package com.ivo.config.dataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 主数据源
 * @author wj
 * @version 1.0
 */
@Configuration
@Slf4j
public class PrimaryDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.primary")
    public DataSource primaryDataSource() {
        log.info("创建主数据源...");
        return DruidDataSourceBuilder.create().build();
    }
}
