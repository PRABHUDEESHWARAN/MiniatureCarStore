package com.project.carstore.cart;

public class CartItemDTO {
    private Long productId;
    private Integer customerId;
    private Integer cartId;

    public CartItemDTO(Long productId, Integer customerId, Integer cartId) {
        this.productId = productId;
        this.customerId = customerId;
        this.cartId = cartId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}