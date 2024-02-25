package com.project.carstore.order;

public class OrderDto {
    private Integer customerId;
    private Integer paymentId;

    public OrderDto(Integer customerId, Integer paymentId) {
        this.customerId = customerId;
        this.paymentId = paymentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
}