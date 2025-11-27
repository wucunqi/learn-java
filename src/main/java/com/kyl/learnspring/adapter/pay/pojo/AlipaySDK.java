package com.kyl.learnspring.adapter.pay.pojo;

public class AlipaySDK {
    // 支付宝的方法名和参数与我们的统一接口完全不同
    public String createPayment(String outTradeNo, String totalAmount) {
        System.out.println("调用支付宝支付接口，订单号：" + outTradeNo + "，金额：" + totalAmount);
        return "ALIPAY_" + System.currentTimeMillis();
    }
    
    public String queryPayment(String outTradeNo) {
        System.out.println("查询支付宝支付状态，订单号：" + outTradeNo);
        return "SUCCESS";
    }
    
    public boolean refundPayment(String outTradeNo, String refundAmount) {
        System.out.println("调用支付宝退款，订单号：" + outTradeNo + "，退款金额：" + refundAmount);
        return true;
    }
}

