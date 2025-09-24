package com.wcq.spring.applicationcontext;

import com.wcq.spring.applicationcontext.pojo.Config;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAnnotationConfigApplicationContext {
    public static void main(String[] args) {

        /*
        创建一个 AnnotationConfigApplicationContext。
        底层构造时会 new 一个 DefaultListableBeanFactory 作为真正的 Bean 工厂。
        把 Config.class 注册为一个 BeanDefinition（会被 ConfigurationClassPostProcessor 解析，进一步注册 @Bean、@ComponentScan 等生成的 BeanDefinition）。
        调用 refresh()，走完整的容器启动流程。
         */
        AnnotationConfigApplicationContext applicationContext1 = new AnnotationConfigApplicationContext(Config.class);

        /*
        传统SpringMVC：容器放到DispatcherServlet中，ServletContext持有Spring根容器的引用，根容器由ContextLoaderListener监听并创建
        SpringBoot：只有一个AnnotationConfigServletWebApplicationContext容器，dispatcherServlet是其中的组件，refresh阶段创建并启动tomcat容器，
        将dispatcherServlet注册到tomcat容器的ServletContext中，dispatcherServlet又持有Spring容器的引用
         */
        AnnotationConfigServletWebApplicationContext applicationContext2 = new AnnotationConfigServletWebApplicationContext(Config.class);

    }
}
