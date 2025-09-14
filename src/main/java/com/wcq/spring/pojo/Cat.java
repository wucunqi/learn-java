package com.wcq.spring.pojo;

import org.springframework.stereotype.Component;

@Component("cat")
public class Cat implements Pet {
    private String name;
    private Integer age;

    @Override
    public void eat() {

    }

    @Override
    public void run() {

    }
}
