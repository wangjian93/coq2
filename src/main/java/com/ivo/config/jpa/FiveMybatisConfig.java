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
 * @author wj
 * @version 1.0
 */
@Configuration
@MapperScan(basePackages = "com.ivo.rest.qms.mapper", sqlSessionFactoryRef = "sqlSessionFactoryFive")
public class FiveMybatisConfig {

    private final DataSource fiveDataSource;

    @Autowired
    public FiveMybatisConfig(@Qualifier("fiveDataSource") DataSource fiveDataSource) {
        this.fiveDataSource = fiveDataSource;
    }

    @Bean(name = "sqlSessionFactoryFive")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(fiveDataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:com/ivo/rest/qms/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean("sqlSessionTemplateFive")
    public SqlSessionTemplate sqlsessiontemplate(@Qualifier("sqlSessionFactoryFive") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
