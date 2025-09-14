package com.wcq;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

public class DemoTest {
/*    @Test
    public void test(){
        String str = "abcdefghijklmnopqrstuvwxyz";
        int count = 200;
        List<String> result = new ArrayList<>();

        for(int i = 0; i < str.length(); i++){
            for(int j = 0; j < count; j++){
                result.add(String.valueOf(str.charAt(i)));
            }
        }

        Collections.shuffle(result);

        for(int i = 0; i < 26; i++){
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream("temp/" + (i + 1) + ".txt")));
            printWriter.print(String.join("\n", result.subList(i * count, (i + 1) * count)));
        }
    }*/

    // @Test
    // public void test2(){
    //     ConcurrentHashMap<String, LongAdder> map = new ConcurrentHashMap<>();
    //
    //     List<Thread> threads = new ArrayList<>();
    //     for(int i = 1; i <= 26; i++){
    //         new Thread(() -> {
    //            // 将文件的所有单词读到List集合中
    //             List<String> words = new ArrayList<>();
    //             for (String word : words) {
    //                 map.computeIfAbsent(words, (k) -> new LongAdder());
    //             }
    //         });
    //     }
    // }
}
