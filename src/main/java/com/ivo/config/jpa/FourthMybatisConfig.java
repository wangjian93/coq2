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
 * 第四数据源mybatis 配置
 * @author wj
 * @version 1.0
 */

@Configuration
@MapperScan(basePackages = "com.ivo.rest.plm.mapper", sqlSessionFactoryRef = "sqlSessionFactoryFourth")
public class FourthMybatisConfig {

    private final DataSource fourthDataSource;

    @Autowired
    public FourthMybatisConfig(@Qualifier("fourthDataSource") DataSource fourthDataSource) {
        this.fourthDataSource = fourthDataSource;
    }

    @Bean(name = "sqlSessionFactoryFourth")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(fourthDataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:com/ivo/rest/plm/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean("sqlSessionTemplateFourth")
    public SqlSessionTemplate sqlsessiontemplate(@Qualifier("sqlSessionFactoryFourth") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
