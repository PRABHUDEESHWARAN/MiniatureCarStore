package com.project.carstore.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    private Integer CustomerId;

    public Integer getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Integer customerId) {
        CustomerId = customerId;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
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

    public Address(Integer customerId, String doorNo, String city, Integer pincode, String state) {
        CustomerId = customerId;
        this.doorNo = doorNo;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
    }

    @Column(name = "DoorNo",nullable = false,length = 3)
    private String doorNo;
    @Column(name = "City",nullable = false,length = 30)
    private String city;
    @Column(name = "Pincode",nullable = false,length = 6)
    private Integer pincode;
    @Column(name = "State",nullable = false,length = 30)
    private String state;

    public Address() {
    }


}
