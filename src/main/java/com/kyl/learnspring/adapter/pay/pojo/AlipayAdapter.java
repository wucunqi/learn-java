package com.kyl.learnspring.adapter.pay.pojo;

/**
 * 支付宝适配器 - 将支付宝SDK适配成统一的PaymentService接口
 */
public class AlipayAdapter implements PaymentService {
    private AlipaySDK alipaySDK;
    
    public AlipayAdapter(AlipaySDK alipaySDK) {
        this.alipaySDK = alipaySDK;
    }
    
    @Override
    public PaymentResult pay(String orderId, double amount) {
        // 适配：转换参数格式，调用支付宝SDK，转换返回结果
        String totalAmount = String.format("%.2f", amount);
        String transactionId = alipaySDK.createPayment(orderId, totalAmount);
        
        PaymentResult result = new PaymentResult();
        result.setSuccess(transactionId != null);
        result.setTransactionId(transactionId);
        result.setMessage("支付宝支付成功");
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
    
    @Override
    public PaymentStatus queryStatus(String orderId) {
        String status = alipaySDK.queryPayment(orderId);
        // 将支付宝的状态映射到我们的统一状态
        switch (status) {
            case "SUCCESS": return PaymentStatus.SUCCESS;
            case "FAILED": return PaymentStatus.FAILED;
            case "PROCESSING": return PaymentStatus.PROCESSING;
            default: return PaymentStatus.UNKNOWN;
        }
    }
    
    @Override
    public RefundResult refund(String orderId, double amount) {
        String refundAmount = String.format("%.2f", amount);
        boolean success = alipaySDK.refundPayment(orderId, refundAmount);
        
        RefundResult result = new RefundResult();
        result.setSuccess(success);
        result.setMessage(success ? "支付宝退款成功" : "支付宝退款失败");
        return result;
    }
}