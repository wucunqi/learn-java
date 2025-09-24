package com.wcq.spring.applicationcontext;

import com.wcq.spring.applicationcontext.pojo.Bean1;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class TestGenericApplicationContext {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        // GenericApplicationContext 提供的API，底层仍然是调用registerBeanDefinition
        context.registerBean("bean1", Bean1.class);

        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // Jakarta
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        /*
        准备 BeanFactory：加载所有 BeanDefinition（你手工注册的那些）。
        执行 BeanFactoryPostProcessor（如果有的话）。
        这一步允许你在 Bean 实例化前，修改 BeanDefinition。
        实例化所有 BeanPostProcessor，并放入容器的后处理器队列。
        这里 AutowiredAnnotationBeanPostProcessor、CommonAnnotationBeanPostProcessor 会被真正实例化。
        实例化非懒加载单例 Bean（比如 bean1）。
        在创建 bean1 的过程中，刚刚注册的 BeanPostProcessor 会介入，处理依赖注入、生命周期注解等。
         */
        context.refresh();
    }
}
