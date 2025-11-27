package com.kyl.learnspring.adapter.pay.pojo;

/**
 * 支付服务工厂 - 根据配置创建对应的支付适配器
 */
public class PaymentServiceFactory {
    public static PaymentService createPaymentService(String channel) {
        switch (channel.toUpperCase()) {
            case "ALIPAY":
                return new AlipayAdapter(new AlipaySDK());
            case "WECHAT":
                return new WechatPayAdapter(new WechatPaySDK());
            default:
                throw new IllegalArgumentException("不支持的支付渠道: " + channel);
        }
    }
}