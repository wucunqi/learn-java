package com.wcq.jvm;

import java.time.LocalDate;

/**
 * javap -v HelloWorld.class
 */
public class HelloWorld {
    private static Integer a = 100;
    private static Double b = 2D;
    private final LocalDate now = LocalDate.now();
    private String name = "kylbase";

    public static void main(String[] args) {

        System.out.println("Hello World!");
    }
}
