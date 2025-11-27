package com.kyl.learnspring.jvm;

public class PreparationTest {
    // a和b都会在初始化阶段赋值
    private static int a;
    private static int b = 5;

    static {
        a = 10;
    }

    // static final 赋值发生在编译时
    // 准备阶段
    private static final int c = 10;

    // 常量池中字面量引用
    private static final String d = "hello";
}
