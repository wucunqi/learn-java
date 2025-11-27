package com.kyl.learnspring.collection;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(10);
        list.add(8);
        list.add(5);
        list.add(99);
        list.add(6);
        list.add(7);

        list.add(1, 10);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }
}
