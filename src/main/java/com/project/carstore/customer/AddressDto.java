package com.project.carstore.customer;

public class AddressDto {
    private Integer customerId;
    private Integer doorNo;
    private String city;
    private Integer pincode;
    private String state;

    public AddressDto(Integer customerId, Integer doorNo, String city, Integer pincode, String state) {
        this.customerId = customerId;
        this.doorNo = doorNo;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(Integer doorNo) {
        this.doorNo = doorNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
