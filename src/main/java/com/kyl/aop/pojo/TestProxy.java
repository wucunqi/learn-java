// package com.kyl.aop.pojo;
//
// import com.kyl.aop.service.UserService;
// import com.kyl.aop.service.impl.UserServiceImpl;
// import org.springframework.cglib.proxy.Enhancer;
// import org.springframework.cglib.proxy.MethodInterceptor;
// import org.springframework.cglib.proxy.MethodProxy;
//
// import java.lang.reflect.InvocationHandler;
// import java.lang.reflect.Method;
// import java.lang.reflect.Proxy;
//
// public class TestProxy {
//     public static void main(String[] args) {
//         /*
//         AOP增强的实现方式：
//             1.代理（运行期间生成代理类的字节码）
//             2.aspectj-maven-plugin编译器插件改写字节码，不能使用的javac编译器（lombok是修改源码抽象语法树AST后生成字节码）
//         代理对象内部维护InvocationHandler、MethodInterceptor（理解：它们本质就是把增强逻辑 和 方法调用 抽象出来外置给使用者
//          */
//
//         UserService userService = new UserServiceImpl();
//
//         // jdk动态代理基于ASM技术直接运行时生成代理类字节码， InvocationHandler可以持有target对象
//         ClassLoader classLoader = UserServiceImpl.class.getClassLoader();
//         UserService userServiceProxy = (UserService) Proxy.newProxyInstance(classLoader, new Class[]{UserService.class}, new InvocationHandler() {
//             // InvocationHandler内部可以持有 被代理的对象
//             @Override
//             public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                 System.out.println("jdk动态代理增强");
//                 Object result = method.invoke(userService, args);
//                 return result;
//             }
//         });
//
//         // final类不能继承，final方法无法增强
//         UserServiceImpl userServiceImplProxy = (UserServiceImpl) Enhancer.create(UserServiceImpl.class, new MethodInterceptor() {
//             // obj 代理类自身, 第四个参数方法代理可以避免反射调用
//             @Override
//             public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//                 System.out.println("cglib动态代理增强");
//                 Object result = method.invoke(userService, args);
//                 return result;
//             }
//         });
//
//         userServiceProxy.addUser();
//         userServiceImplProxy.addUser();
//     }
// }
