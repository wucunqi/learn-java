package com.wcq.spring.applicationcontext;

import com.wcq.spring.applicationcontext.pojo.Config;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;

public class TestAnnotationConfigApplicationContext {
    public static void main(String[] args) {
        // AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        // 非懒加载
        // for (String beanDefinitionName : context.getBeanDefinitionNames()) {
        //     System.out.println(beanDefinitionName);
        // }

        /* 传统SpringMVC：容器放到DispatcherServlet中，ServletContext持有Spring根容器的引用，根容器由ContextLoaderListener监听并创建
         SpringBoot：只有一个AnnotationConfigServletWebApplicationContext容器，dispatcherServlet是其中的组件，refresh阶段创建并启动tomcat容器，
         将dispatcherServlet注册到tomcat容器的ServletContext中，dispatcherServlet又持有Spring容器的引用
         */
        AnnotationConfigServletWebApplicationContext context = new AnnotationConfigServletWebApplicationContext(Config.class);

    }
}
