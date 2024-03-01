package com.project.carstore.cart;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer customerId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();
    private Double totalPrice;
    private Integer totalItems;

    public Integer getCustomerId() {
        return customerId;
    }

    public Cart() {
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Cart(Integer customerId) {
        this.customerId = customerId;
    }

    public Cart(Set<CartItem> cartItems, Double totalPrice, Integer totalItems) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
}