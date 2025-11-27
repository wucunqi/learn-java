package com.kyl.learnspring.jvm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class LoadingTest {
    public static void main(String[] args) {
        try {
            // 会触发类的加载、链接和初始化
            Class<?> clazz = Class.forName("java.lang.String");
            Method[] ms = clazz.getDeclaredMethods();
            for (Method m : ms) {
                Annotation[] annotations = m.getAnnotations();
                for (Annotation annotation : annotations) {
                    System.out.println(annotation.annotationType().getSimpleName());
                }

                // m.getModifiers()返回int
                String mod = Modifier.toString(m.getModifiers());
                System.out.print(mod + " ");

                String returnType = m.getReturnType().getSimpleName();
                System.out.print(returnType + " ");

                System.out.print(m.getName() + "(");

                Class<?>[] parameterTypes = m.getParameterTypes();
                if(parameterTypes.length == 0){
                    System.out.print(')');
                }
                for (int i = 0; i < parameterTypes.length; i++) {
                    char end = (i == parameterTypes.length - 1) ?  ')' : ',';
                    System.out.print(parameterTypes[i].getSimpleName() + end);
                }
                System.out.println();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
