package com.wcq;

import com.wcq.spring.pojo.Pet;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<Pet> animals = ServiceLoader.load(Pet.class);
        for (Pet pet : animals) {
            pet.eat();
            pet.run();
        }

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionBuilder.genericBeanDefinition()
                .getBeanDefinition();
        beanFactory.registerBeanDefinition("dog", null);
    }
}
