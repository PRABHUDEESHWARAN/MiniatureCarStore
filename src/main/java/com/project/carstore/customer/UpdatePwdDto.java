package com.project.carstore.customer;

public class UpdatePwdDto {
    private Integer customerId;
    private String oldPassword;
    private String newPassword;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public UpdatePwdDto(Integer customerId, String oldPassword, String newPassword) {
        this.customerId = customerId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
