package com.kyl.aop.pojo.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 定义切面类，可以包含 多个切点和通知
 * 最终转化为低级的Advisor
 */
@Slf4j
@Component
@Aspect
public class CustomAspect {

    @Before("execution(* com.kyl.aop.service.impl.UserServiceImpl.addUser())")
    public void before(){
        log.info("===before===");
    }

    @After("execution(* com.kyl.aop.service.impl.UserServiceImpl.addUser())")
    public void after(){
        log.info("===after===");
    }
}
