package com.project.carstore.cart;

import com.project.carstore.product.ProductException;

import java.util.Optional;

public interface CartService {
    public Optional<Cart> getCartById(Integer cartId) throws CartException;

    Integer CreateCartForCustomer(Integer id);
    public Cart addCartItemToCart(CartItemDTO cartItemDTO)throws ProductException;
    public Optional<CartItem> getCartItemByProductId(Long ProductId);
}