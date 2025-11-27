package com.kyl.learnspring.aware.pojo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Config {

    /**
     * 观察这个注解失效
     */
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        log.info("setApplicationContext");
    }

    // Aware是内置流程 InitializingBean
    // ImportBeanDefinitionRegistrar专门用于导入配置类，而它自己是通过@Import使用的
}
