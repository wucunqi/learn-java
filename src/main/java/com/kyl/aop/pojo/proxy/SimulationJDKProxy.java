package com.kyl.aop.pojo.proxy;

import com.kyl.aop.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 模拟jdk代理对象内部结构
 */
public class SimulationJDKProxy extends Proxy implements UserService {

    public SimulationJDKProxy(InvocationHandler h) {
        super(h);
    }


    @Override
    public Integer addUser() {
        Method addUser = null;
        try {
            // 可以把方法申明为静态成员变量，在静态代码块中初始化
            addUser = UserService.class.getMethod("addUser");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            return (Integer) h.invoke(this, addUser, new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer updateUser() {
        return null;
    }
}
