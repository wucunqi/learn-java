package com.kyl.learnspring.bfpp.pojo;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.wcq.spring.beanfactorypostprocessor.pojo")
public class Config {
    // @Bean
    // public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
    //     // SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    // }


    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.wcq.spring.beanfactorypostprocessor.mapper");
        return mapperScannerConfigurer;
    }

}
