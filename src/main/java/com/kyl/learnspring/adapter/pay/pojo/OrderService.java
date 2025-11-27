package com.kyl.learnspring.adapter.pay.pojo;

/**
 * 业务层代码 - 完全不需要关心底层是哪个支付渠道
 */
public class OrderService {
    
    public void processPayment(String orderId, double amount, String payChannel) {
        // 获取支付服务 - 业务层只依赖统一的PaymentService接口
        PaymentService paymentService = PaymentServiceFactory.createPaymentService(payChannel);
        
        // 统一的支付调用
        PaymentResult result = paymentService.pay(orderId, amount);
        
        if (result.isSuccess()) {
            System.out.println("支付成功，交易号：" + result.getTransactionId());
            // 更新订单状态等业务逻辑...
        } else {
            System.out.println("支付失败：" + result.getMessage());
            // 失败处理逻辑...
        }
    }
    
    public void checkPaymentStatus(String orderId, String payChannel) {
        PaymentService paymentService = PaymentServiceFactory.createPaymentService(payChannel);
        PaymentStatus status = paymentService.queryStatus(orderId);
        System.out.println("订单 " + orderId + " 的支付状态：" + status);
    }
}
