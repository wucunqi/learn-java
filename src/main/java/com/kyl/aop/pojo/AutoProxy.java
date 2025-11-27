package com.kyl.aop.pojo;

import com.kyl.aop.service.UserService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
/*
springboot默认启用
@Import({AspectJAutoProxyRegistrar.class})
 */
@EnableAspectJAutoProxy
public class AutoProxy {
    public static void main(String[] args) {
        /*
        Spring容器启动流程：
        - 创建SpringApplication实例
        - 检查类路径是否存在特定类，确定ApplicationContext类型
        - 读取spring.factories 用类加载器加载这些类，调用clazz的无参构造函数创建 Initializer、Listener并放入集合（它们是Spring的工具类）
        - 启动监听器
        - 创建Environment并加载配置
        - 打印Banner
        - 创建ApplicationContext
            - 调用AnnotationConfigUtils.registerAnnotationConfigProcessors() 添加BeanDefinition
        - 调用ApplicationContextInitializer.initialize(context)方法，注册额外的PropertySource、BeanDefinition
        - 注册主类的BeanDefinition
        - refresh()
            - invokeBeanFactoryPostProcessors() 创建实例并执行
            例如ConfigurationClassPostProcessor（BeanDefinitionRegistryPostProcessor接口 extends 它是BeanFactoryPostProcessor）
            解析所有带@Configuration注解的类、处理@ComponentScan、处理@Import（包括@EnableAutoConfiguration读取imports的自动配置类）、处理 @PropertySource，把解析出的类都注册成 BeanDefinition
            - registerBeanPostProcessors() 创建实例并注册到BeanPostProcessor链表
            - finishBeanFactoryInitialization() 实例化其他普通Bean


        2. ConfigurationClassPostProcessor 实现了 BeanDefinitionRegistryPostProcessor接口，它是BeanFactoryPostProcessor的子接口，在BeanFactoryPostProcessor执行之前
        扫描@Configuration、@Component、@Service等注解，并解析配置类中的@ComponentScan、@Bean、@Import等注解 → 创建Bean定义
        3. 发现@Aspect注解的类 → 识别为切面Advisor
        4. 解析@Before、@Around等注解 → 构建Advice（通知）
        5. 创建Bean时，对匹配的Bean创建代理对象
        6. 将代理对象放入容器，替代原始Bean
         */

        /*
        配置类上@Import支持三种类：
        1. 普通配置类
        2. ImportBeanDefinitionRegistrar 将其实例化后统一registerBeanDefinition方法
        3. ImportSelector 由EnvironmentImportSelector调用selectImports方法
        */

        ConfigurableApplicationContext applicationContext = SpringApplication.run(AutoProxy.class, args);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.addUser();

        /*
        代理对象创建时机：创建 -> （*）依赖注入 -> 初始化（*），依赖注入前创建的是依赖的代理对象
        BeanPostProcessor，扫描容器中所有的Bean，根据AOP规则（如@Aspect、@Transactional、@Around等）决定是否为Bean创建代理对象
        低级通知：AspectJAroundAdvice AspectJMethodBeforeAdvice AspectJAfterReturningAdvice AspectJAfterThrowingAdvice AspectJAfterAdvice
        低级切点：AspectJExpressionPointcut
        低级切面：DefaultPointcutAdvisor
         */
        AnnotationAwareAspectJAutoProxyCreator autoProxyCreator = applicationContext.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        /* AnnotationAwareAspectJAutoProxyCreator 实现了SmartInstantiationAwareBeanPostProcessor 和 BeanFactoryAware
        职责：委托内部属性BeanFactoryAspectJAdvisorsBuilder扫描容器中所有 @AspectJ 等注解的切面类，提取出Advisor列表，
        拦截Bean的创建过程，从advisor缓存列表匹配（第一次需要扫描创建缓存列表），使用ProxyFactory创建代理对象（包含Advisor）
        执行代理对象方法时 才把所有的通知转为 环绕通知MethodInterceptor（基于适配器：MethodBeforeAdviceAdapter等）
        */
        System.out.println(autoProxyCreator);
    }


    @Bean
    public Advisor advisor(MethodInterceptor advice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.wcq.spring.aop.pojo.UserServiceImpl.addUser())");
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public MethodInterceptor advice() {
        return new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("环绕通知开始");
                Object result = invocation.proceed();
                System.out.println("环绕通知结束");
                return result;
            }
        };
    }
}
