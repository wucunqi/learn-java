package com.wcq.jvm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeapSnapshot {
    public static void main(String[] args) throws IOException {
        List<Object> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        System.in.read();
        // 去执行jmap
        list = null;
        System.in.read();
        // 去执行jmap
    }
}
