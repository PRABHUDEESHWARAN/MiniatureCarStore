package com.project.carstore.cart;

public class UpdateCartItemDTO {
    private Integer cartId;
    private Integer cartItemId;
    private Integer newQuantity;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(Integer newQuantity) {
        this.newQuantity = newQuantity;
    }

    public UpdateCartItemDTO(Integer cartId, Integer cartItemId, Integer newQuantity) {
        this.cartId = cartId;
        this.cartItemId = cartItemId;
        this.newQuantity = newQuantity;
    }
}
