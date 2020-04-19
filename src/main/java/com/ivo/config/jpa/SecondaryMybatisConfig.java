package com.ivo.config.jpa;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 第二数据源mybatis 配置
 * @author wj
 * @version 1.0
 */

@Configuration
@MapperScan(basePackages = "com.ivo.rest.oracle.mapper", sqlSessionFactoryRef = "sqlSessionFactorySecondary")
public class SecondaryMybatisConfig {

    private final DataSource SecondaryDataSource;

    @Autowired
    public SecondaryMybatisConfig(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        this.SecondaryDataSource = secondaryDataSource;
    }

    @Bean(name = "sqlSessionFactorySecondary")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(SecondaryDataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:com/ivo/rest/oracle/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean("sqlSessionTemplateSecondary")
    public SqlSessionTemplate sqlsessiontemplate(@Qualifier("sqlSessionFactorySecondary") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
