package com.kyl.learnspring.adapter.pay.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付SDK - 原始接口2
 */
public class WechatPaySDK {
    // 微信支付的接口又是另外一套风格
    public Map<String, Object> unifiedOrder(String mchOrderNo, int totalFee) {
        System.out.println("调用微信统一下单，商户订单号：" + mchOrderNo + "，金额(分)：" + totalFee);
        Map<String, Object> result = new HashMap<>();
        result.put("return_code", "SUCCESS");
        result.put("transaction_id", "WECHAT_" + System.currentTimeMillis());
        return result;
    }
    
    public String orderQuery(String transactionId) {
        System.out.println("查询微信支付订单，交易号：" + transactionId);
        return "PAID";
    }
    
    public Map<String, String> refund(String transactionId, int refundFee) {
        System.out.println("调用微信退款，交易号：" + transactionId + "，退款金额(分)：" + refundFee);
        Map<String, String> result = new HashMap<>();
        result.put("result", "SUCCESS");
        return result;
    }
}