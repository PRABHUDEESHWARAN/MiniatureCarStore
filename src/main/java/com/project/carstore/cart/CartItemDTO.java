package com.project.carstore.cart;

public class CartItemDTO {
    private Long productId;

    private Integer cartId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public CartItemDTO(Long productId, Integer cartId) {
        this.productId = productId;
        this.cartId = cartId;
    }
}