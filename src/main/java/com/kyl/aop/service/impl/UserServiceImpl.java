package com.kyl.aop.service.impl;

import com.kyl.aop.service.OrderService;
import com.kyl.aop.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final OrderService orderService;
    @Override
    public Integer addUser() {
        System.out.println("addUser");
        orderService.selectOrderById();
        return null;
    }

    @Override
    public Integer updateUser() {
        System.out.println("updateUser");
        return null;
    }

}
