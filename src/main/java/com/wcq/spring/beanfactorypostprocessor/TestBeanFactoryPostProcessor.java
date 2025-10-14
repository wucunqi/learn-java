package com.wcq.spring.beanfactorypostprocessor;

import com.wcq.spring.beanfactorypostprocessor.pojo.Config;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class TestBeanFactoryPostProcessor {
    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();

        applicationContext.registerBean("config", Config.class);

        // 添加beanFactory后处理器解析@ComponentScan、@Bean、@Import、@ImportResource
        applicationContext.registerBean(ConfigurationClassPostProcessor.class);

        /*
        无论是使用 @Mapper 还是 @MapperScan，都是由 MapperScannerConfigurer注册 BeanDefinition 的工作。
        1. MapperScannerConfigurer 实现 BeanDefinitionRegistryPostProcessor接口 扫描类路径下所有带有 @Mapper 注解的接口, 添加BeanDefinition
        由MybatisPlusAutoConfiguration 自动配置代替了注册MapperScannerConfigurer的工作
        自动配置还注册了SqlSessionFactory、SqlSessionTemplate 等
        2. @MapperScan 底层 @Import(MapperScannerRegistrar.class)
        MapperScannerRegistrar 会根据 @MapperScan 的配置（如 basePackages），显示地注册一个配置好的 MapperScannerConfigurer Bean 到Spring容器中。

        BeanDefinition的beanClass属性是org.mybatis.spring.mapper.MapperFactoryBean，Spring会寻找FactoryBean（MapperFactoryBean）,
        调用getObject()方法返回Mapper接口的代理对象，所有生成的 Mapper 代理对象都需要一个 SqlSessionFactory 来执行 SQL。
         */
        applicationContext.registerBean(MapperScannerConfigurer.class);

        applicationContext.refresh();
    }
}
