package com.kyl.learnspring.adapter.pay.pojo;

/**
 * 退款结果
 */
public class RefundResult {
    private boolean success;
    private String refundId;
    private String message;
    private long timestamp;
    
    // 默认构造方法
    public RefundResult() {
        this.timestamp = System.currentTimeMillis();
    }
    
    // 带参数构造方法
    public RefundResult(boolean success, String refundId, String message) {
        this.success = success;
        this.refundId = refundId;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getter 和 Setter 方法
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getRefundId() {
        return refundId;
    }
    
    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "RefundResult{" +
                "success=" + success +
                ", refundId='" + refundId + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
    
    // 一些便捷的静态工厂方法
    public static RefundResult success(String refundId) {
        return new RefundResult(true, refundId, "退款成功");
    }
    
    public static RefundResult success(String refundId, String message) {
        return new RefundResult(true, refundId, message);
    }
    
    public static RefundResult failure(String message) {
        return new RefundResult(false, null, message);
    }
}