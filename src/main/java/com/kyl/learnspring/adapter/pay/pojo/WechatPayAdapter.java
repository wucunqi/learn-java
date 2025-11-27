package com.kyl.learnspring.adapter.pay.pojo;

import java.util.Map;

/**
 * 微信支付适配器
 */
public class WechatPayAdapter implements PaymentService {
    private WechatPaySDK wechatPaySDK;
    
    public WechatPayAdapter(WechatPaySDK wechatPaySDK) {
        this.wechatPaySDK = wechatPaySDK;
    }
    
    @Override
    public PaymentResult pay(String orderId, double amount) {
        // 适配：元转分，调用微信SDK，转换返回结果格式
        int totalFee = (int) (amount * 100); // 微信支付需要分为单位
        Map<String, Object> response = wechatPaySDK.unifiedOrder(orderId, totalFee);
        
        PaymentResult result = new PaymentResult();
        String returnCode = (String) response.get("return_code");
        boolean success = "SUCCESS".equals(returnCode);
        result.setSuccess(success);
        result.setTransactionId((String) response.get("transaction_id"));
        result.setMessage(success ? "微信支付成功" : "微信支付失败");
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
    
    @Override
    public PaymentStatus queryStatus(String orderId) {
        String status = wechatPaySDK.orderQuery(orderId);
        switch (status) {
            case "PAID": return PaymentStatus.SUCCESS;
            case "FAIL": return PaymentStatus.FAILED;
            case "USERPAYING": return PaymentStatus.PROCESSING;
            default: return PaymentStatus.UNKNOWN;
        }
    }
    
    @Override
    public RefundResult refund(String orderId, double amount) {
        int refundFee = (int) (amount * 100);
        Map<String, String> response = wechatPaySDK.refund(orderId, refundFee);
        
        RefundResult result = new RefundResult();
        boolean success = "SUCCESS".equals(response.get("result"));
        result.setSuccess(success);
        result.setMessage(success ? "微信退款成功" : "微信退款失败");
        return result;
    }
}