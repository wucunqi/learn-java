package com.wcq.spring.beanfactory.pojo;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Data
public class House {
    @Resource(name = "cat")
    @Autowired
    @Qualifier("dog")
    private Pet pet;
}
