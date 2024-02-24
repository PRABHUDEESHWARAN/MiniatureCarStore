package com.project.carstore.customer;

import com.project.carstore.cart.Cart;
import com.project.carstore.order.Order;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "FirstName",nullable = false,length = 50)
    private String firstname;

    @Column(name = "LastName",nullable = false,length = 50)
    private String lastname;

    @Column(name = "Email",nullable = false,length = 200)
    private String email;

    @Column(name = "password",nullable = false,length = 15)
    private String password;

    @Column(name = "MobileNumber",nullable = false,length = 10)
    private Integer mobileNo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> address=new ArrayList<>();

    private Integer cartId;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> CustomerOrders=new ArrayList<Order>();

    public List<Order> getCustomerOrders() {
        return CustomerOrders;
    }


    public void setCustomerOrders(List<Order> customerOrders) {
        CustomerOrders = customerOrders;
    }

    public Customer(String firstname, String lastname, String email, String password, Integer mobileNo) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.mobileNo = mobileNo;

    }

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Integer mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}
