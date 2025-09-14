package com.wcq;

import org.junit.jupiter.api.Test;

public class OSTest {
    @Test
    public void test(){
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU 核心数: " + cores);
    }
}
