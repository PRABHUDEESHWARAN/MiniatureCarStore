package com.project.carstore.order;

import com.project.carstore.product.Product;
import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @OneToOne(cascade = CascadeType.MERGE)
//    private Product product;
    private Long ProductId;
    private Integer quantity;
    private Double totalPrice;
    private Integer orderId;

    public OrderItem() {
    }

    public OrderItem(Long productId, Integer quantity, Double price, Integer orderId) {
        this.ProductId = productId;
        this.quantity = quantity;
        this.totalPrice = price;
        this.orderId = orderId;
    }

    public OrderItem(Product product, int quantity) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getProductId() {
        return ProductId;
    }

    public void setProductId(Long productId) {
        ProductId = productId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}