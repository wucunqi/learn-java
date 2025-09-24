package com.wcq.spring.beanfactory;

import com.wcq.spring.beanfactory.pojo.House;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.core.Ordered;

import java.util.Map;

public class TestListableBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(House.class).getBeanDefinition();
        beanFactory.registerBeanDefinition("house", beanDefinition);

        // 框架内部使用，向容器中注入处理注解的后处理器的 BeanDefinition
        /*
            ConfigurationClassPostProcessor
            处理 @Configuration, @Import, @ComponentScan, @PropertySource 等配置类相关注解。
            ---
            AutowiredAnnotationBeanPostProcessor
            处理 @Autowired, @Value。
            ---
            CommonAnnotationBeanPostProcessor
            处理 @Resource, @PostConstruct, @PreDestroy。
            ---
            EventListenerMethodProcessor / DefaultEventListenerFactory
            处理 @EventListener。
         */
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        Map<String, BeanFactoryPostProcessor> map1 = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : map1.values()) {
            /*
            Spring 容器启动过程中，在所有 BeanDefinition 已经加载到 BeanFactory，但还没有实例化任何 Bean 之前，回调这个接口。
            你可以在这里对 BeanFactory 里的 BeanDefinition 元数据 做修改、增强、替换
             */
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }

        // 构造bean的某些阶段（依赖注入） 被调用
        /*
        在实例化过程中，如果某个 BeanPostProcessor 实现了 BeanFactoryAware 或者 ApplicationContextAware
        Spring 会自动回调 setBeanFactory() 或 setApplicationContext() 方法，把 beanFactory 注入进去。
         */
        Map<String, BeanPostProcessor> map2 = beanFactory.getBeansOfType(BeanPostProcessor.class);
        beanFactory.addBeanPostProcessors(map2.values());

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan("com.wcq.spring");

        CommonAnnotationBeanPostProcessor commonPostProcessor = beanFactory.getBean(CommonAnnotationBeanPostProcessor.class);
        AutowiredAnnotationBeanPostProcessor autowiredPostProcessor = beanFactory.getBean(AutowiredAnnotationBeanPostProcessor.class);
        commonPostProcessor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        // 注入元数据在BeanPostProcessor执行前就已确定，调整执行顺序无法改变由哪个后处理器做依赖注入
        System.out.println(beanFactory.getBean(House.class).getPet());
    }
}
