package com.wcq.spring.aop.pojo;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Integer addUser() {
        System.out.println("addUser");
        return null;
    }

    @Override
    public Integer updateUser() {
        System.out.println("updateUser");
        return null;
    }
}
