package com.project.carstore.order;

import com.project.carstore.customer.Address;
import com.project.carstore.customer.Customer;
import com.project.carstore.payment.PaymentDetails;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Customer customer;

    @OneToMany
    private List<OrderItem> orderItem=new ArrayList<>();
    private String paymentStatus;
    private Integer totalPrice;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    @OneToOne
    private Address address;

    @OneToOne
    private PaymentDetails paymentDetails;
    private Boolean orderStatus;
    private Integer totalItems;
    private LocalDate createdAt;

    public Order() {
    }

    public Order(Integer id, Customer customer, List<OrderItem> orderItem, String paymentStatus, Integer totalPrice, LocalDate orderDate, LocalDate deliveryDate, Address address, PaymentDetails paymentDetails, Boolean orderStatus, Integer totalItems, LocalDate createdAt) {
        this.id = id;
        this.customer = customer;
        this.orderItem = orderItem;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.address = address;
        this.paymentDetails = paymentDetails;
        this.orderStatus = orderStatus;
        this.totalItems = totalItems;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
