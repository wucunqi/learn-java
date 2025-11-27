package com.kyl.learnspring.adapter.pay.pojo;


import lombok.Data;

@Data
public class PaymentResult {
    private boolean success;
    private String transactionId;
    private String message;
    private long timestamp;

}

