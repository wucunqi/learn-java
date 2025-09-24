package com.wcq.jvm;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class TestReference {
    private static final Map<String, SoftReference<byte[]>> cache = new HashMap<>();

    public static void main(String[] args) {
        byte[] image = new byte[10 * 1024 * 1024];
        cache.put("image", new SoftReference<>(image));
    }

    private void put(String key, byte[] value){
        cache.put(key, new SoftReference<>(value));
    }

    private byte[] get(String key){
        return cache.get(key).get();
    }
}
