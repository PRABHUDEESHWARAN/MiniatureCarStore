package com.project.carstore.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class CardInfo {
    private String cardholderName;
    @Id
    private Integer cardInfoId;
    private Integer cardNumber;
    private LocalDate expiryDate;

    public Integer getCardInfoId() {
        return cardInfoId;
    }

    public void setCardInfoId(Integer cardInfoId) {
        this.cardInfoId = cardInfoId;
    }

    private Integer cvv;

    public CardInfo(String cardholderName, Integer cardInfoId, Integer cardNumber, LocalDate expiryDate, Integer cvv) {
        this.cardholderName = cardholderName;
        this.cardInfoId = cardInfoId;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public CardInfo() {
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }
}
