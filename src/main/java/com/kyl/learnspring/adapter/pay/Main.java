package com.kyl.learnspring.adapter.pay;

import com.kyl.learnspring.adapter.pay.pojo.OrderService;

/**
 * 测试类
 */
public class Main {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        
        // 使用支付宝支付
        System.out.println("=== 支付宝支付 ===");
        orderService.processPayment("ORDER_001", 199.99, "alipay");
        
        System.out.println("\n=== 微信支付 ===");
        // 使用微信支付
        orderService.processPayment("ORDER_002", 299.50, "wechat");
        
        System.out.println("\n=== 查询支付状态 ===");
        orderService.checkPaymentStatus("ORDER_001", "alipay");
    }
}