package com.project.carstore.order;

import com.project.carstore.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class OrderItem {
    @Id
    private Integer id;

    @OneToOne
    private Product product;
    private Integer quantity;
    private Integer price;
    private Integer orderId;

    public OrderItem() {
    }

    public OrderItem(Integer id, Product product, Integer quantity, Integer price, Integer orderId) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}