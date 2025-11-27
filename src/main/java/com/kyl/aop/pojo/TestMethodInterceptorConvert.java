// package com.kyl.aop.pojo;
//
// import com.kyl.aop.pojo.pointcut.CustomAspect;
// import com.kyl.aop.service.impl.UserServiceImpl;
// import org.aspectj.lang.annotation.Before;
// import org.springframework.aop.Advisor;
// import org.springframework.aop.aspectj.AspectJExpressionPointcut;
// import org.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
// import org.springframework.aop.framework.ProxyFactory;
// import org.springframework.aop.support.DefaultPointcutAdvisor;
//
// import java.lang.reflect.Method;
// import java.util.ArrayList;
// import java.util.List;
//
// /*
// 会把所有的通知转位环绕通知MethodInterceptor
//  */
// public class TestMethodInterceptorConvert {
//     public static void main(String[] args) throws NoSuchMethodException {
//         List<Advisor> advisors = new ArrayList<>();
//
//         // .class方式会加载但不初始化
//         Method[] methods = CustomAspect.class.getDeclaredMethods();
//         for (Method method : methods) {
//             if(method.isAnnotationPresent(Before.class)){
//                 String expression = method.getAnnotation(Before.class).value();
//                 AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//                 pointcut.setExpression(expression);
//                 AspectJMethodBeforeAdvice advice = new AspectJMethodBeforeAdvice(method, pointcut, null);
//                 Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
//                 advisors.add(advisor);
//             }
//         }
//
//         // 最终是由ProxyFactory创建的代理对象，代理对象创建好了，执行增强方法的时候才会得到转换后的MethodInterceptor
//         ProxyFactory proxyFactory = new ProxyFactory();
//         proxyFactory.setTarget(new UserServiceImpl());
//         proxyFactory.addAdvisors(advisors);
//         List<Object> methodInterceptorList = proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(UserServiceImpl.class.getMethod("addUser"), UserServiceImpl.class);
//
//         /*
//         MethodBeforeAdviceAdapter
//          */
//     }
//
// }
