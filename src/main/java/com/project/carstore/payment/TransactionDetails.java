package com.project.carstore.payment;

public class TransactionDetails {
    private String  transactionId;
    private String currency;
    private Double amount;
    private String key;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TransactionDetails(String transactionId, String currency, Double amount, String key) {
        this.transactionId = transactionId;
        this.currency = currency;
        this.amount = amount;
        this.key = key;
    }
}
