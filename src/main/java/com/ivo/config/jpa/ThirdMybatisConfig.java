package com.ivo.config.jpa;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 第三数据源mybatis配置
 * @author wj
 * @version 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.ivo.rest.eifdb.mapper", "com.ivo.rest.bm.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryThird")
public class ThirdMybatisConfig {

    private final DataSource thirdDataSource;

    @Autowired
    public ThirdMybatisConfig(@Qualifier("thirdDataSource") DataSource thirdDataSource) {
        this.thirdDataSource = thirdDataSource;
    }

    @Bean(name = "sqlSessionFactoryThird")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(thirdDataSource);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources1 = pathMatchingResourcePatternResolver.getResources("classpath*:com/ivo/rest/eifdb/mapper/*.xml");
        Resource[] resources2 = pathMatchingResourcePatternResolver.getResources("classpath*:com/ivo/rest/bm/mapper/*.xml");
        bean.setMapperLocations(ArrayUtils.addAll(resources1, resources2));
        return bean.getObject();
    }

    @Bean("sqlSessionTemplateThird")
    public SqlSessionTemplate sqlsessiontemplate(@Qualifier("sqlSessionFactoryThird") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
