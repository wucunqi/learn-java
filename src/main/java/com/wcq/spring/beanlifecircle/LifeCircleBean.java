package com.wcq.spring.beanlifecircle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LifeCircleBean {
    /*
    观察方法执行顺序
    BeanPostProcessor为Bean的生命周期各个阶段提供扩展
     */
    public LifeCircleBean() {
        log.info("构造方法执行");
    }

    @Autowired
    public void dependencyInjection(@Value("${JAVA_HOME}") String javaHome) {
        log.info("依赖注入:" + javaHome);
    }

    @PostConstruct
    public void init() {
        log.info("初始化方法执行");
    }

    @PreDestroy
    public void destroy() {
        log.info("销毁方法执行");
    }
}
