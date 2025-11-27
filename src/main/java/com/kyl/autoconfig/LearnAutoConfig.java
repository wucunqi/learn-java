package com.kyl.autoconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author wucunqi
 */
@SpringBootApplication
public class LearnAutoConfig {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(LearnAutoConfig.class, args);
    }
}
