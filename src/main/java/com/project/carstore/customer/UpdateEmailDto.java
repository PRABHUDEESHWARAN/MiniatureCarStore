package com.project.carstore.customer;

public class UpdateEmailDto {
    private Integer customerId;
    private String newEmail;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public UpdateEmailDto(Integer customerId, String newEmail) {
        this.customerId = customerId;
        this.newEmail = newEmail;
    }
}
