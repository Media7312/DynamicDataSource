package com.example.demo.service.conf;

import com.alibaba.druid.support.ibatis.DruidDataSourceFactory;
import com.example.demo.conf.DyamicDataSource;
import com.example.demo.conf.DyamicPlugin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Author: 侯玢
 * @Description:已经判断query、update用于确定数据源，此处获取数据源配置
 * @Date: Created in 9:33 2018/3/26
 */
@Configuration
@Import(DyamicPlugin.class)
public class SqlSessionConf {
    //数据眼配置，包括基本设置+独立设置（url user pass）
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public java.util.Properties datasource(){
        java.util.Properties properties = new java.util.Properties();
        convertProperties(properties);
        return properties;
    }
    @Bean("datasourcewrite")
    @ConfigurationProperties(prefix = "spring.datasourcewrite")
    public java.util.Properties datasourcewrite() throws Exception{
        java.util.Properties properties = new java.util.Properties();
        properties.putAll(datasource());
        convertProperties(properties);
        return properties;
    }

    @Bean("datasourceread")
    @ConfigurationProperties(prefix = "spring.datasourceread")
    public java.util.Properties datasourceread() throws Exception{
        java.util.Properties properties = new java.util.Properties();
        properties.putAll(datasource());
        convertProperties(properties);
        return properties;
    }

    @Bean
    @Qualifier("dyamicDataSource")
    public DataSource dyamicDataSource(java.util.Properties datasourceread, java.util.Properties datasourcewrite) throws Exception{
        DyamicDataSource dyamicDataSource = new DyamicDataSource();
        dyamicDataSource.setReadDataSource(com.alibaba.druid.pool.DruidDataSourceFactory.createDataSource(datasourceread));
        dyamicDataSource.setWriteDataSource(com.alibaba.druid.pool.DruidDataSourceFactory.createDataSource(datasourcewrite));
        return dyamicDataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplateMete(Properties datasourceread) throws Exception {
        return new JdbcTemplate(com.alibaba.druid.pool.DruidDataSourceFactory.createDataSource(datasourceread));
    }

    private void convertProperties(java.util.Properties properties){
        properties.forEach((k,v)-> properties.setProperty(k.toString(),String.valueOf(v)));
    }
}
