package com.project.carstore.payment;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    private String paymentMethod;
    private Integer orderId;
    private Boolean status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CardInfo> cardInfo;


    public PaymentDetails() {
    }

    public PaymentDetails(String paymentMethod, Integer orderId, Boolean status, List<CardInfo> cardInfo) {

        this.paymentMethod = paymentMethod;
        this.orderId = orderId;
        this.status = status;
        this.cardInfo = cardInfo;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CardInfo> getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(List<CardInfo> cardInfo) {
        this.cardInfo = cardInfo;
    }
}
