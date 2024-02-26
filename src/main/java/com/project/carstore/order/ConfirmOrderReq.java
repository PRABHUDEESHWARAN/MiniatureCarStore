package com.project.carstore.order;

public class ConfirmOrderReq {
    private String transactionId;
    private Integer orderId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public ConfirmOrderReq(String transactionId, Integer orderId) {
        this.transactionId = transactionId;
        this.orderId = orderId;
    }
}
