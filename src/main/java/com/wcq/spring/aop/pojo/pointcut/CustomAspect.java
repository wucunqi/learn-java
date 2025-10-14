package com.wcq.spring.aop.pojo.pointcut;

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

    @Before("execution(* com.wcq.spring.aop.pojo.UserServiceImpl.addUser())")
    public void before(){
        log.info("===before===");
        log.info("函数执行开始时间：{}", System.currentTimeMillis() / 1000);
    }

    @After("execution(* com.wcq.spring.aop.pojo.UserServiceImpl.addUser())")
    public void after(){
        log.info("===after===");
        log.info("函数执行结束时间：{}", System.currentTimeMillis() / 1000);
    }
}
