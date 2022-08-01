package com.ruoyi.job.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    /**
     * 源数据库配置
     * @return
     */
    @Bean(name = "ssheDataSource")
    @Qualifier("ssheDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.sshe")
    public DataSource ssheDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ssheDataSourceJdbcTemplate")
    public JdbcTemplate ssheDataSourceJdbcTemplate(
            @Qualifier("ssheDataSource")DataSource dataSource
    ){
        return new JdbcTemplate(dataSource);
    }

    /**
     * 需同步的数据库配置
     * @return
     */
    @Bean(name = "xxglDataSource")
    @Qualifier("xxglDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.xxgl")
    public DataSource xxglDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "xxglDataSourceJdbcTemplate")
    public JdbcTemplate xxglDataSourceJdbcTemplate(
            @Qualifier("xxglDataSource")DataSource dataSource
    ){
        return new JdbcTemplate(dataSource);
    }


}
