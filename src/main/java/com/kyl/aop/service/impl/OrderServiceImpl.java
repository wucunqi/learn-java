package com.kyl.aop.service.impl;

import com.kyl.aop.service.OrderService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wucunqi
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    @Async
    @Transactional
    public void selectOrderById() {
        System.out.println("start...");
        System.out.println("selectOrderById");
        System.out.println("end...");
    }
}
