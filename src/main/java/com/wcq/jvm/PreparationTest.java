package com.wcq.jvm;

public class PreparationTest {
    // a和b都会在初始化阶段赋值，准备阶段会赋一个默认值
    private static int a;
    private static int b = 5;

    static {
        a = 10;
    }

    // 准备阶段
    private static final int c = 10;
    private static final String d = "hello";
}
