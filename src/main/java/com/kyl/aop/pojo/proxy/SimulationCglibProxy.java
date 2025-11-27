// package com.kyl.aop.pojo.proxy;
//
//
// import com.kyl.aop.service.impl.UserServiceImpl;
// import org.springframework.cglib.proxy.MethodInterceptor;
//
// import java.lang.reflect.Method;
//
// /**
//  * 模拟cglib代理对象内部结构
//  */
// public class SimulationCglibProxy extends UserServiceImpl {
//
//     // 也有可能是一个list
//     private MethodInterceptor methodInterceptor;
//
//     public SimulationCglibProxy(MethodInterceptor methodInterceptor) {
//         this.methodInterceptor = methodInterceptor;
//     }
//
//     // 重写父类方法
//     @Override
//     public Integer addUser() {
//         // 可以把方法申明为静态成员变量，在静态代码块中初始化
//         Method addUser = null;
//         try {
//             addUser = UserServiceImpl.class.getMethod("addUser");
//         } catch (NoSuchMethodException e) {
//             throw new RuntimeException(e);
//         }
//         try {
//             return (Integer) methodInterceptor.intercept(this, addUser, new Object[0], null);
//         } catch (Throwable e) {
//             throw new RuntimeException(e);
//         }
//     }
//
//     public Integer addUserSuper(){
//         return super.addUser();
//     }
// }
//
