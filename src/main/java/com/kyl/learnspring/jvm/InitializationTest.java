package com.kyl.learnspring.jvm;

public class InitializationTest {
    // 非静态字段 不生成<clinit>方法
    public int num = 1;

    // 没有显示赋值 不生成<clinit>方法
    public static int num1;

    public static final int num2 = 1;
}
