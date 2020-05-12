package com.tonytaotao.springboot.dubbo.common.config;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * mybatis-plus增强配置
 * @author tonytaotao
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * SQL注入器
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }


    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        try {

            GlobalConfig globalConfig = new GlobalConfig();
            GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
            dbConfig.setIdType(IdType.AUTO);
            dbConfig.setSelectStrategy(FieldStrategy.NOT_NULL);
            dbConfig.setInsertStrategy(FieldStrategy.NOT_NULL);
            dbConfig.setUpdateStrategy(FieldStrategy.NOT_NULL);
            dbConfig.setTableUnderline(true);
            dbConfig.setLogicDeleteValue("0");
            dbConfig.setLogicNotDeleteValue("1");
            globalConfig.setDbConfig(dbConfig);

            MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
            mybatisConfiguration.setMapUnderscoreToCamelCase(true);
            mybatisConfiguration.setCacheEnabled(false);

            MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
            mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);
            mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
            mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/com/tonytaotao/springboot/dubbo/*/*/mapper/xml/*Mapper.xml"));
            mybatisSqlSessionFactoryBean.setTypeAliasesPackage("com.tonytaotao.springboot.dubbo.*.*.entity");
            mybatisSqlSessionFactoryBean.setDataSource(dataSource);
            return mybatisSqlSessionFactoryBean;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
