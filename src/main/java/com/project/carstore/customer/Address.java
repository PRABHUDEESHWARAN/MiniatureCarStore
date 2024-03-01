package com.project.carstore.customer;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    private Integer customerId;
    @Column(nullable = false)
    private Integer doorNo;
    @Column(nullable = false,length = 30)
    private String city;
    @Column(nullable = false,length = 6)
    private Integer pincode;
    @Column(nullable = false,length = 30)
    private String state;

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

    public Address(Integer customerId, Integer doorNo, String city, Integer pincode, String state) {
        this.customerId = customerId;
        this.doorNo = doorNo;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
    }



    public Address() {
    }


}