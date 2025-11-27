package com.kyl.learnspring.beanfactory.pojo;

import org.springframework.stereotype.Component;

@Component
public class Dog implements Pet {
    private String name;
    private Integer age;
    @Override
    public void eat() {
        System.out.println("Dog eat");
    }

    @Override
    public void run() {
        System.out.println("Dog run");
    }
}
