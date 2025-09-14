package com.wcq.spring;

import com.wcq.spring.pojo.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAnnotationConfigApplicationContext {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }
}
