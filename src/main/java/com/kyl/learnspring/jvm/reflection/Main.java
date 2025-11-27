package com.kyl.learnspring.jvm.reflection;

import com.kyl.learnspring.jvm.reflection.pojo.Reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method func = Reflect.class.getMethod("func");
        for(int i = 0; i < 100; i++){
            func.invoke(null);
        }
    }
}
