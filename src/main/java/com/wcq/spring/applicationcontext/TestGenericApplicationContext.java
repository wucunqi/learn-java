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

        // 执行BeanFactoryPostProcessor、实例化BeanPostProcessor并放入队列、实例化非懒加载bean
        context.refresh();
    }
}
