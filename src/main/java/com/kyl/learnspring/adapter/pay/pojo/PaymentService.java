package com.kyl.learnspring.adapter.pay.pojo;

/**
 * 统一的支付服务接口 - 这是客户端期望的接口
 */
public interface PaymentService {

    PaymentResult pay(String orderId, double amount);

    PaymentStatus queryStatus(String orderId);

    RefundResult refund(String orderId, double amount);
}
